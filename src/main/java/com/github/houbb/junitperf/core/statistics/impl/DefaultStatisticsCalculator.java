package com.github.houbb.junitperf.core.statistics.impl;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;
import org.apiguardian.api.API;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认统计计算
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class DefaultStatisticsCalculator implements StatisticsCalculator {

    private static final long serialVersionUID = 3715867392352544936L;

    //region private fields
    /**
     * 耗时统计方式
     * @since 1.0.0
     */
    private final DescriptiveStatistics latencyStatistics;

    /**
     * 执行评价计数
     */
    private final AtomicLong evaluationCount = new AtomicLong();

    /**
     * 错误计数
     */
    private final AtomicLong errorCount = new AtomicLong();

    /**
     * 内存消耗
     * @since 2.0.5
     */
    private volatile long memoryKb;

    //endregion

    //region constructor
    /**
     * 默认构造器
     * @since 2.0.5
     */
    public DefaultStatisticsCalculator() {
        this(new SynchronizedDescriptiveStatistics());
    }

    /**
     * 私有构造器
     * @param latencyStatistics 延迟统计
     * @since 1.0.0
     */
    private DefaultStatisticsCalculator(final DescriptiveStatistics latencyStatistics) {
        this.latencyStatistics = latencyStatistics;
    }
    //endregion

    //region methods
    @Override
    public void addLatencyMeasurement(long executionTimeNs) {
        latencyStatistics.addValue(executionTimeNs);
    }

    @Override
    public void incrementErrorCount() {
        errorCount.incrementAndGet();
    }

    @Override
    public long getErrorCount() {
        return errorCount.get();
    }

    @Override
    public float getErrorPercentage() {
        return ((float)getErrorCount() / getEvaluationCount()) * 100;
    }

    @Override
    public void incrementEvaluationCount() {
        evaluationCount.incrementAndGet();
    }

    @Override
    public long getEvaluationCount() {
        return evaluationCount.get();
    }

    @Override
    public float getLatencyPercentile(int percentile, TimeUnit unit) {
        return (float) latencyStatistics.getPercentile((double)(percentile)) / unit.toNanos(1);
    }

    @Override
    public float getMaxLatency(TimeUnit unit) {
        return (float) latencyStatistics.getMax() / unit.toNanos(1);
    }

    @Override
    public float getMinLatency(TimeUnit unit) {
        return (float) latencyStatistics.getMin() / unit.toNanos(1);
    }

    @Override
    public float getMeanLatency(TimeUnit unit) {
        return (float) latencyStatistics.getMean() / unit.toNanos(1);
    }

    @Override
    public void setMemory(long memoryKb) {
        this.memoryKb = memoryKb;
    }

    @Override
    public long getMemory() {
        return this.memoryKb;
    }
    //endregion

}
