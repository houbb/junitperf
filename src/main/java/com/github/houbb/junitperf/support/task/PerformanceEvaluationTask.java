package com.github.houbb.junitperf.support.task;

import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import org.junit.runners.model.Statement;

import static java.lang.System.nanoTime;

/**
 * 性能测试-Task
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class PerformanceEvaluationTask implements Runnable {

    private long warmUpNs;

    private final Statement statement;

    private StatisticsCalculator statisticsCalculator;

    public PerformanceEvaluationTask(long warmUpNs, Statement statement, StatisticsCalculator statisticsCalculator) {
        this.warmUpNs = warmUpNs;
        this.statement = statement;
        this.statisticsCalculator = statisticsCalculator;
    }

    @Override
    public void run() {
        long startTimeNs = System.nanoTime();
        long startMeasurements = startTimeNs + warmUpNs;
        while (!Thread.currentThread().isInterrupted()) {
            evaluateStatement(startMeasurements);
        }
    }

    /**
     * 执行校验
     * @param startMeasurements
     */
    private void evaluateStatement(long startMeasurements) {
        //1. 准备阶段
        if (nanoTime() < startMeasurements) {
            try {
                statement.evaluate();
            } catch (Throwable throwable) {
                // IGNORE
            }
        } else {
            long startTimeNs = nanoTime();
            try {
                statement.evaluate();
                statisticsCalculator.addLatencyMeasurement(nanoTime() - startTimeNs);
                statisticsCalculator.incrementEvaluationCount();
            } catch (InterruptedException e) { // NOSONAR
                // IGNORE - no metrics
            } catch (Throwable throwable) {
                statisticsCalculator.incrementEvaluationCount();
                statisticsCalculator.incrementErrorCount();
                statisticsCalculator.addLatencyMeasurement(nanoTime() - startTimeNs);
            }
        }
    }
}
