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

    /**
     * 热身时间
     */
    private long warmUpNs;

    /**
     * junit statement
     */
    private final Statement statement;

    /**
     * 统计计算者
     */
    private StatisticsCalculator statisticsCalculator;

    /**
     * 是否继续标志位
     */
    private volatile boolean isContinue;

    public PerformanceEvaluationTask(long warmUpNs, Statement statement, StatisticsCalculator statisticsCalculator) {
        this.warmUpNs = warmUpNs;
        this.statement = statement;
        this.statisticsCalculator = statisticsCalculator;
        this.isContinue = true; //默认创建时继续执行
    }

    @Override
    public void run() {
        long startTimeNs = System.nanoTime();
        long startMeasurements = startTimeNs + warmUpNs;
        while (isContinue) {
            evaluateStatement(startMeasurements);
        }
    }

    /**
     * 执行校验
     * @param startMeasurements 开始时间
     */
    private void evaluateStatement(long startMeasurements) {
        //0. 如果继续执行为 false，退出执行。
        if(!isContinue) {
            return;
        }

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
                statisticsCalculator.addLatencyMeasurement(getCostTimeNs(startTimeNs));
                statisticsCalculator.incrementEvaluationCount();
            } catch (InterruptedException e) { // NOSONAR
                // IGNORE - no metrics
            } catch (Throwable throwable) {
                statisticsCalculator.incrementEvaluationCount();
                statisticsCalculator.incrementErrorCount();
                statisticsCalculator.addLatencyMeasurement(getCostTimeNs(startTimeNs));
            }
        }
    }

    /**
     * 获取消耗的时间(单位：毫秒)
     * @param startTimeNs 开始时间
     * @return 消耗的时间
     */
    private long getCostTimeNs(long startTimeNs) {
        long currentTimeNs = System.nanoTime();
        return currentTimeNs - startTimeNs;
    }

    //region getter & setter
    public boolean isContinue() {
        return isContinue;
    }

    public void setContinue(boolean aContinue) {
        isContinue = aContinue;
    }
    //endregion
}
