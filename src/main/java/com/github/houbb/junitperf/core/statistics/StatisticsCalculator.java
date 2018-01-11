package com.github.houbb.junitperf.core.statistics;

import java.util.concurrent.TimeUnit;

/**
 * 统计计算接口
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public interface StatisticsCalculator {

    /**
     * 累加延迟的时间
     * @param executionTimeNs
     */
    void addLatencyMeasurement(long executionTimeNs);

    /**
     * 增加错误总次数
     */
    void incrementErrorCount();

    /**
     * 获取错误总次数
     * @return long
     */
    long getErrorCount();

    /**
     * 获取错误的百分比
     * @return float
     */
    float getErrorPercentage();

    /**
     * 增加校验的总数
     */
    void incrementEvaluationCount();

    /**
     * 获取校验的总数
     * @return long
     */
    long getEvaluationCount();

    /**
     * 获取延迟百分比
     * @param percentile
     * @param unit
     * @return
     */
    float getLatencyPercentile(int percentile, TimeUnit unit);

    /**
     * 获取最大延迟
     * @param unit
     * @return
     */
    float getMaxLatency(TimeUnit unit);

    /**
     * 获取最小延迟
     * @param unit
     * @return
     */
    float getMinLatency(TimeUnit unit);

    /**
     * 获取平均延迟
     * @param unit
     * @return
     */
    float getMeanLatency(TimeUnit unit);

}
