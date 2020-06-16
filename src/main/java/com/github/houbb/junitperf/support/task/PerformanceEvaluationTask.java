package com.github.houbb.junitperf.support.task;

import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;

import org.apache.lucene.util.RamUsageEstimator;
import org.apiguardian.api.API;

import java.lang.management.MemoryUsage;
import java.lang.reflect.Method;

import static java.lang.System.nanoTime;

/**
 * 性能测试-Task
 *
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
@API(status = API.Status.INTERNAL)
public class PerformanceEvaluationTask implements Runnable {

    /**
     * 热身时间
     */
    private final long warmUpNs;

    /**
     * 统计计算者
     */
    private final StatisticsCalculator statisticsCalculator;

    /**
     * 是否继续标志位
     */
    private volatile boolean isContinue;

    /**
     * 测试实例
     */
    private final Object testInstance;

    /**
     * 测试方法
     */
    private final Method testMethod;

    /**
     * 构造器
     * @param warmUpNs 准备时间
     * @param statisticsCalculator 统计
     * @param testInstance 测试实例
     * @param testMethod 测试方法
     * @since 1.0.0
     */
    public PerformanceEvaluationTask(long warmUpNs, StatisticsCalculator statisticsCalculator,
                                     Object testInstance, Method testMethod) {
        this.warmUpNs = warmUpNs;
        this.statisticsCalculator = statisticsCalculator;
        this.testInstance = testInstance;
        this.testMethod = testMethod;
        //默认创建时继续执行
        this.isContinue = true;
    }

    @Override
    public void run() {
        long startTimeNs = System.nanoTime();
        long startMeasurements = startTimeNs + warmUpNs;

        // 堆大小
        long memoryKb = RamUsageEstimator.shallowSizeOf(testInstance);
        statisticsCalculator.setMemory(memoryKb);

        while (isContinue) {
            evaluateStatement(startMeasurements);
        }
    }

    /**
     * 执行校验
     *
     * @param startMeasurements 开始时间
     */
    private void evaluateStatement(long startMeasurements) {
        //0. 如果继续执行为 false，退出执行。
        if (!isContinue) {
            return;
        }

        //1. 准备阶段
        if (nanoTime() < startMeasurements) {
            try {
                testMethod.invoke(testInstance);
            } catch (Exception throwable) {
                // IGNORE
            }
        } else {
            long startTimeNs = nanoTime();
            try {
                testMethod.invoke(testInstance);

                commonStatisticsUpdate(startTimeNs);
            } catch (Exception throwable) {
                // 错误信息更新
                statisticsCalculator.incrementErrorCount();

                commonStatisticsUpdate(startTimeNs);
            }
        }
    }

    /**
     * 通用的统计更新
     * @param startTimeNs 开始时间
     * @since 2.0.5
     */
    private void commonStatisticsUpdate(final long startTimeNs) {
        statisticsCalculator.incrementEvaluationCount();
        statisticsCalculator.addLatencyMeasurement(getCostTimeNs(startTimeNs));
    }

    /**
     * 获取消耗的时间(单位：毫秒)
     *
     * @param startTimeNs 开始时间
     * @return 消耗的时间
     * @since 1.0.0
     */
    private long getCostTimeNs(long startTimeNs) {
        long currentTimeNs = System.nanoTime();
        return currentTimeNs - startTimeNs;
    }

    public boolean isContinue() {
        return isContinue;
    }

    public void setContinue(boolean aContinue) {
        isContinue = aContinue;
    }

}
