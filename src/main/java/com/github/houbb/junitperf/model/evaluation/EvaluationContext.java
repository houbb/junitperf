package com.github.houbb.junitperf.model.evaluation;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.paradise.common.util.ArrayUtil;
import com.github.houbb.paradise.common.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import java.util.Map;

/**
 * 评价接口定义，用于展现最后的性能评价结果。
 * 所有的结果直接继承此接口，可用于生成对应的报告信息。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class EvaluationContext {

    /**
     * 测试方法名称
     */
    private final String methodName;

    /**
     * 开始时间
     */
    private final String startTime;

    public EvaluationContext(String methodName, String startTime) {
        this.methodName = methodName;
        this.startTime = startTime;
    }

    //region 配置相关属性
    private int configThreads;

    private long configWarmUp;

    private long configDuration;

    public int getConfigThreads() {
        return configThreads;
    }

    public long getConfigWarmUp() {
        return configWarmUp;
    }

    public long getConfigDuration() {
        return configDuration;
    }

    //endregion

    //region 评判相关属性
    private float requireMin;
    private float requireMax;
    private float requireAverage;
    private int requireTimesPerSecond;
    private Map<Integer, Float> requirePercentilesMap;


    //endregion


    /**
     * 统计者
     */
    private StatisticsCalculator statisticsCalculator;

    public StatisticsCalculator getStatisticsCalculator() {
        return statisticsCalculator;
    }

    public void setStatisticsCalculator(StatisticsCalculator statisticsCalculator) {
        this.statisticsCalculator = statisticsCalculator;
    }

    /**
     * 加载配置
     * @param junitPerfConfig 配置注解
     */
    public void loadConfig(JunitPerfConfig junitPerfConfig) {
        validateJunitPerfConfig(junitPerfConfig);

        configThreads = junitPerfConfig.threads();
        configWarmUp = junitPerfConfig.warmUp();
        configDuration = junitPerfConfig.duration();
    }

    /**
     * 加载评判标准
     * @param junitPerfRequire 评判注解
     */
    public void loadRequire(JunitPerfRequire junitPerfRequire) {
        if(ObjectUtil.isNotNull(junitPerfRequire)) {
            validateJunitPerfRequire(junitPerfRequire);

            requireMin = junitPerfRequire.min();
            requireMax = junitPerfRequire.max();
            requireAverage = junitPerfRequire.average();
            requireTimesPerSecond = junitPerfRequire.timesPerSecond();
            requirePercentilesMap = parseRequirePercentilesMap(junitPerfRequire.percentiles());
        }
    }

    /**
     * 校验配置属性
     * @param junitPerfConfig
     */
    private void validateJunitPerfConfig(JunitPerfConfig junitPerfConfig) {

    }

    /**
     * 校验请求属性
     * @param junitPerfRequire
     */
    private void validateJunitPerfRequire(JunitPerfRequire junitPerfRequire) {

    }


    /**
     * 转换需求的 map
     * @param percentiles 百分比信息数组
     * @return map
     */
    private Map<Integer, Float> parseRequirePercentilesMap(String[] percentiles) {
        Map<Integer, Float> percentilesMap = Maps.newHashMap();
        if(ArrayUtil.isNotEmpty(percentiles)) {
            try {
                for(String percent : percentiles) {
                    String[] strings = percent.split(":");
                    Integer left = Ints.tryParse(strings[0]);
                    Float right = Floats.tryParse(strings[1]);
                    percentilesMap.put(left, right);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Percentiles format is error! please like this: 80:50000.");
            }
        }

        return percentilesMap;
    }

    /**
     * 运行校验
     * 1. 必须保证统计这一步已经做完了
     */
    public void runValidation() {
        //....todo: 校验
    }

}
