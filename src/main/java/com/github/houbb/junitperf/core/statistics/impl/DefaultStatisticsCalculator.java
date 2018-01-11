package com.github.houbb.junitperf.core.statistics.impl;

import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认统计计算
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class DefaultStatisticsCalculator implements StatisticsCalculator {

    //region private fields
    /**
     * 统计方式
     */
    private final DescriptiveStatistics statistics;

    /**
     * 执行评价计数
     */
    private final AtomicLong evaluationCount = new AtomicLong();

    /**
     * 错误计数
     */
    private final AtomicLong errorCount = new AtomicLong();
    //endregion

    //region constructor
    public DefaultStatisticsCalculator() {
        this(new SynchronizedDescriptiveStatistics());
    }

    public DefaultStatisticsCalculator(DescriptiveStatistics statistics) {
        this.statistics = statistics;
    }
    //endregion

    //region methods
    @Override
    public void addLatencyMeasurement(long executionTimeNs) {

    }

    @Override
    public void incrementErrorCount() {

    }

    @Override
    public long getErrorCount() {
        return 0;
    }

    @Override
    public float getErrorPercentage() {
        return 0;
    }

    @Override
    public void incrementEvaluationCount() {

    }

    @Override
    public long getEvaluationCount() {
        return 0;
    }

    @Override
    public float getLatencyPercentile(int percentile, TimeUnit unit) {
        return 0;
    }

    @Override
    public float getMaxLatency(TimeUnit unit) {
        return 0;
    }

    @Override
    public float getMinLatency(TimeUnit unit) {
        return 0;
    }

    @Override
    public float getMeanLatency(TimeUnit unit) {
        return 0;
    }
    //endregion

}
