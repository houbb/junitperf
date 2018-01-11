package com.github.houbb.junitperf.support.statements;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.support.task.PerformanceEvaluationTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.runners.model.Statement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

/**
 * 性能测试 statement
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 * @see com.github.houbb.junitperf.core.rule.JunitPerfRule 用于此规则
 */
public class PerformanceEvaluationStatement extends Statement {

    private static final String THREAD_NAME_PATTERN = "performance-evaluation-thread-%d";
    private static final ThreadFactory FACTORY = new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_PATTERN).build();

    private final EvaluationContext evaluationContext;
    private final Statement statement;
    private final StatisticsCalculator statisticsCalculator;
    private final Set<Reporter> reporterSet;
    private final Set<EvaluationContext> evaluationContextSet;

    /**
     * 性能测试接口定义
     * @param evaluationContext
     * @param statement
     * @param statisticsCalculator
     * @param reporterSet
     * @param evaluationContextSet
     */
    public PerformanceEvaluationStatement(EvaluationContext evaluationContext,
                                          Statement statement,
                                          StatisticsCalculator statisticsCalculator,
                                          Set<Reporter> reporterSet,
                                          Set<EvaluationContext> evaluationContextSet) {
        this.evaluationContext = evaluationContext;
        this.statement = statement;
        this.statisticsCalculator = statisticsCalculator;

        //报告生成
        this.reporterSet = reporterSet;
        this.evaluationContextSet = evaluationContextSet;
    }

    @Override
    public void evaluate() throws Throwable {
        List<Thread> threadList = new LinkedList<>();

        try {
            for(int i = 0; i < evaluationContext.getConfigThreads(); i++) {
                PerformanceEvaluationTask task = new PerformanceEvaluationTask(evaluationContext.getConfigWarmUp(),
                        statement, statisticsCalculator);
                Thread t = FACTORY.newThread(task);
                threadList.add(t);
                t.start();
            }

            Thread.sleep(evaluationContext.getConfigDuration());
        } catch (InterruptedException e) {
            for(Thread thread : threadList) {
                thread.isInterrupted();
            }
        }

        evaluationContext.setStatisticsCalculator(statisticsCalculator);
        evaluationContext.runValidation();
        generateReportor();
        assertThresholdsMet();
    }

    /**
     * 报告生成
     */
    private void generateReportor() {
        for(Reporter reporter : reporterSet) {
            reporter.report(evaluationContextSet);
        }
    }

    /**
     * 断言满足阈值
     */
    private void assertThresholdsMet() {
        //1. per second
        //2. percents
    }

}
