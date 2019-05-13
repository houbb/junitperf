package com.github.houbb.junitperf.core.rule;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.core.statistics.impl.DefaultStatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;
import com.github.houbb.junitperf.support.statements.PerformanceEvaluationStatement;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Sets.newHashSet;

/**
 * 性能测试规则
 * 此方法类似于一个 aop 切面，在每个执行方法上面生效。
 * (1) 保证线程安全
 * (2) JDK 1.7+
 *
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0
 */
public class JunitPerfRule implements TestRule {

    //region private fields
    /**
     * 统计计算方式
     */
    private final StatisticsCalculator statisticsCalculator;

    /**
     * 报告生成方式
     */
    private final Set<Reporter> reporterSet;

    /**
     * 用于存储上下文
     */
    private static final ConcurrentHashMap<Class, Set<EvaluationContext>> ACTIVE_CONTEXTS = new ConcurrentHashMap<>();
    //endregion


    //region constructor

    /**
     * 默认构造器
     * 1. 使用默认的统计方式 {@link DefaultStatisticsCalculator}
     * 2. 使用 {@link HtmlReporter} 生成统计报表
     */
    public JunitPerfRule() {
        this(new DefaultStatisticsCalculator(), new HtmlReporter());
    }

    /**
     * 1. 默认统计方式为 {@link DefaultStatisticsCalculator}
     * 2. 生成方式为用户指定方式
     *
     * @param reporters 生成报告方式
     */
    public JunitPerfRule(Reporter... reporters) {
        this(new DefaultStatisticsCalculator(), newHashSet(reporters));
    }

    /**
     * 为了便于用户使用
     *
     * @param statisticsCalculator 统计计算方式
     * @param reporters            生成报告方式
     */
    public JunitPerfRule(StatisticsCalculator statisticsCalculator, Reporter... reporters) {
        this(statisticsCalculator, newHashSet(reporters));
    }

    /**
     * 完整的构造器
     *
     * @param statisticsCalculator 统计计算方式
     * @param reporterSet          生成报告方式
     */
    public JunitPerfRule(StatisticsCalculator statisticsCalculator, Set<Reporter> reporterSet) {
        this.statisticsCalculator = statisticsCalculator;
        this.reporterSet = reporterSet;
    }
    //endregion

    @Override
    public Statement apply(Statement statement, Description description) {
        Statement activeStatement = statement;
        JunitPerfConfig junitPerfConfig = description.getAnnotation(JunitPerfConfig.class);
        JunitPerfRequire junitPerfRequire = description.getAnnotation(JunitPerfRequire.class);

        if (ObjectUtil.isNotNull(junitPerfConfig)) {
            // Group test contexts by test class
            ACTIVE_CONTEXTS.putIfAbsent(description.getTestClass(), new HashSet<EvaluationContext>());

            EvaluationContext evaluationContext = new EvaluationContext(description.getMethodName(), DateUtil.getSimpleDateStr());
            evaluationContext.loadConfig(junitPerfConfig);
            evaluationContext.loadRequire(junitPerfRequire);
            ACTIVE_CONTEXTS.get(description.getTestClass()).add(evaluationContext);

            activeStatement = new PerformanceEvaluationStatement(evaluationContext,
                    statement,
                    statisticsCalculator,
                    reporterSet,
                    ACTIVE_CONTEXTS.get(description.getTestClass()),
                    description.getTestClass()
            );
        }

        return activeStatement;
    }

}
