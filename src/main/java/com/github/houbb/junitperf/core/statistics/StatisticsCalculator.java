package com.github.houbb.junitperf.core.statistics;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 统计计算接口
 * 备注：所有的实现需要提供无参数构造器
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
@API(status = API.Status.INTERNAL)
public interface StatisticsCalculator extends Serializable {

    /**
     * 累加延迟的时间
     * @param executionTimeNs 执行时间(纳秒)
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
     * @param percentile 百分比
     * @param unit 时间单位
     * @return 延迟百分比
     */
    float getLatencyPercentile(int percentile, TimeUnit unit);

    /**
     * 获取最大延迟
     * @param unit 时间单位
     * @return 最大延迟
     */
    float getMaxLatency(TimeUnit unit);

    /**
     * 获取最小延迟
     * @param unit 时间单位
     * @return 最小延迟
     */
    float getMinLatency(TimeUnit unit);

    /**
     * 获取平均延迟
     * @param unit 时间单位
     * @return 平均延迟
     */
    float getMeanLatency(TimeUnit unit);

}
