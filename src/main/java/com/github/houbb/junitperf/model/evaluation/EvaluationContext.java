package com.github.houbb.junitperf.model.evaluation;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.paradise.common.util.ArrayUtil;
import com.github.houbb.paradise.common.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newTreeMap;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 评价接口定义，用于展现最后的性能评价结果。
 * 所有的结果直接继承此接口，可用于生成对应的报告信息。
 * 备注：我觉得这部分的代码写的不合理，应该拆分为贫血模型。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class EvaluationContext implements Serializable {

    /**
     * 测试方法名称
     */
    private final String methodName;

    /**
     * 开始时间
     */
    private final String startTime;

    //region 评价结果
    /**
     * 最小延迟是否符合
     */
    private boolean isMinAchieved;
    /**
     * 最大延迟是否符合
     */
    private boolean isMaxAchieved;
    /**
     * 平均延迟是否符合
     */
    private boolean isAverageAchieved;
    /**
     * 每秒执行次数是否符合
     */
    private boolean isTimesPerSecondAchieved;
    /**
     * 百分比阈值结果
     */
    private boolean isPercentilesAchieved;

    /**
     * 验证是否成功
     * 备注：当所有的校验通过则视为通过
     */
    private boolean isSuccessful;
    //endregion


    /**
     * 统计者
     */
    private StatisticsCalculator statisticsCalculator;

    //region 配置相关属性
    /**
     * 配置-线程数
     */
    private int configThreads;
    /**
     * 配置-准备时间
     */
    private long configWarmUp;
    /**
     * 配置-运行时间
     */
    private long configDuration;
    //endregion

    //region 评判相关属性
    /**
     * 最小延迟
     */
    private float requireMin;
    /**
     * 最大延迟
     */
    private float requireMax;
    /**
     * 平均延迟
     */
    private float requireAverage;
    /**
     * 每秒运行次数
     */
    private int requireTimesPerSecond;
    /**
     * 百分比测试需求
     */
    private Map<Integer, Float> requirePercentilesMap;
    /**
     * 百分比测试结果
     */
    private Map<Integer, Boolean> requirePercentilesResults;
    //endregion



    public EvaluationContext(String methodName, String startTime) {
        this.methodName = methodName;
        this.startTime = startTime;
    }



    //region getter and setter
    public int getConfigThreads() {
        return configThreads;
    }

    public long getConfigWarmUp() {
        return configWarmUp;
    }

    public long getConfigDuration() {
        return configDuration;
    }

    public StatisticsCalculator getStatisticsCalculator() {
        return statisticsCalculator;
    }

    public void setStatisticsCalculator(StatisticsCalculator statisticsCalculator) {
        this.statisticsCalculator = statisticsCalculator;
    }

    public float getRequireMin() {
        return requireMin;
    }

    public float getRequireMax() {
        return requireMax;
    }

    public float getRequireAverage() {
        return requireAverage;
    }

    public int getRequireTimesPerSecond() {
        return requireTimesPerSecond;
    }

    public Map<Integer, Float> getRequirePercentilesMap() {
        return requirePercentilesMap;
    }

    public Map<Integer, Boolean> getRequirePercentilesResults() {
        return requirePercentilesResults;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getStartTime() {
        return startTime;
    }
    //endregion

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
        } else {
            requireMin = -1;
            requireMax = -1;
            requireAverage = -1;
            requireTimesPerSecond = -1;
            requirePercentilesMap = newHashMap();   //避免使用NPE
            requirePercentilesResults = newHashMap();
        }

    }

    /**
     * 校验配置属性
     * @param junitPerfConfig config 注解信息
     */
    private void validateJunitPerfConfig(JunitPerfConfig junitPerfConfig) {
        checkNotNull(junitPerfConfig, "JunitPerfConfig must not be null!");

        int threads = junitPerfConfig.threads();
        long warmUp = junitPerfConfig.warmUp();
        long duration = junitPerfConfig.duration();
        checkState(duration > 0, "duration must be > 0ms.");
        checkState(warmUp >= 0, "warmUp must be >= 0ms.");
        checkState(warmUp < duration, "warmUp must be < duration.");
        checkState(threads > 0, "threads must be > 0.");
    }

    /**
     * 校验请求属性
     * @param junitPerfRequire require 注解信息
     */
    private void validateJunitPerfRequire(JunitPerfRequire junitPerfRequire) {
        checkState(junitPerfRequire.timesPerSecond() >= 0, "timesPerSecond must be >= 0");
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
                    Integer left = Ints.tryParse(strings[0]);   //消耗时间
                    Float right = Floats.tryParse(strings[1]);  //百分比例
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
        //0. assert 统计已经计算

        //1. 是否满足 require 信息
        isMinAchieved = isMinAchieved();
        isAverageAchieved = isAverageAchieved();
        isMaxAchieved = isMaxAchieved();
        isTimesPerSecondAchieved = isTimesPerSecondAchieved();
        isPercentilesAchieved = isPercentilesAchieved();

        //2. 是否成功
        isSuccessful = isSuccessful();
    }

    //region 验证结果
    /**
     * 获取执行次数QPS
     */
    @SuppressWarnings("WeakerAccess")
    public long getThroughputQps() {
        return (long)(((float)statisticsCalculator.getEvaluationCount() / ((float)configDuration - configWarmUp)) * 1000);
    }


    /**
     * 延迟校验
     * @param actualNs 实际时间(纳秒)
     * @param requiredMs 实际时间(毫秒)
     * @return {@code true} 是
     */
    private boolean validateLatency(float actualNs, float requiredMs) {
        long thresholdNs = (long)(requiredMs * MILLISECONDS.toNanos(1));
        return requireMax < 0 || actualNs <= thresholdNs;
    }

    /**
     * 最小延迟是否符合
     * @return {@code true} 是
     */
    public boolean isMinAchieved() {
        return validateLatency(statisticsCalculator.getMinLatency(TimeUnit.NANOSECONDS), requireMin);
    }
    /**
     * 最大延迟是否符合
     * @return {@code true} 是
     */
    public boolean isMaxAchieved(){
        return validateLatency(statisticsCalculator.getMaxLatency(TimeUnit.NANOSECONDS), requireMax);
    }

    /**
     * 平均延迟是否符合
     * @return {@code true} 是
     */
    public boolean isAverageAchieved() {
        return validateLatency(statisticsCalculator.getMeanLatency(TimeUnit.NANOSECONDS), requireAverage);
    }

    /**
     * 每秒执行次数是否符合
     * @return {@code true} 是
     */
    public boolean isTimesPerSecondAchieved() {
        return requireTimesPerSecond < 0 || getThroughputQps() >= requireTimesPerSecond;
    }
    /**
     * 百分比阈值是否满足
     * @return {@code true} 是
     */
    private boolean isPercentilesAchieved() {
        requirePercentilesResults = newTreeMap();

        //1. 计算结果
        for(Map.Entry<Integer, Float> entry : requirePercentilesMap.entrySet()) {
            //todo: 这个地方可能存在BUG 需要约定一致性。设计的有些迷茫
            //和解析同时定义。
            Integer percentile = entry.getKey();
            float thresholdMs = entry.getValue();   //限制的时间
            long thresholdNs = (long) (thresholdMs * MILLISECONDS.toNanos(1));    //
            boolean result = statisticsCalculator.getLatencyPercentile(percentile, NANOSECONDS) <= thresholdNs;
            requirePercentilesResults.put(percentile, result);
        }


        //2. 校验是否通过
        for(Boolean bool : requirePercentilesResults.values()) {
            if(!bool) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否成功
     * @return {@code true} 是
     */
    public boolean isSuccessful() {
        return isMinAchieved
        && isMaxAchieved
        && isAverageAchieved
        && isTimesPerSecondAchieved
        && isPercentilesAchieved;
    }
    //endregion

}
