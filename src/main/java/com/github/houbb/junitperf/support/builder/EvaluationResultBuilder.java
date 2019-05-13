package com.github.houbb.junitperf.support.builder;

import com.github.houbb.heaven.support.builder.IBuilder;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationConfig;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationRequire;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationResult;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Maps.newTreeMap;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 验证结果-构建者
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
public class EvaluationResultBuilder implements IBuilder<EvaluationResult> {

    /**
     * 配置
     */
    private final EvaluationConfig evaluationConfig;

    /**
     * 限定
     */
    private final EvaluationRequire evaluationRequire;

    /**
     * 统计者
     */
    private final StatisticsCalculator statisticsCalculator;

    public EvaluationResultBuilder(EvaluationConfig evaluationConfig,
                                   EvaluationRequire evaluationRequire,
                                   StatisticsCalculator statisticsCalculator) {
        this.evaluationConfig = evaluationConfig;
        this.evaluationRequire = evaluationRequire;
        this.statisticsCalculator = statisticsCalculator;
    }

    @Override
    public EvaluationResult build() {
        EvaluationResult evaluationResult = new EvaluationResult();
        evaluationResult.setMinAchieved(isMinAchieved());
        evaluationResult.setMaxAchieved(isMaxAchieved());
        evaluationResult.setAverageAchieved(isAverageAchieved());
        evaluationResult.setTimesPerSecondAchieved(isTimesPerSecondAchieved());

        Map<Integer, Boolean> isPercentilesAchievedMap = buildIsPercentilesAchievedMap();
        evaluationResult.setIsPercentilesAchievedMap(isPercentilesAchievedMap);
        evaluationResult.setPercentilesAchieved(isPercentilesAchieved(isPercentilesAchievedMap));
        evaluationResult.setSuccessful(isSuccessful(evaluationResult));
        evaluationResult.setThroughputQps(getThroughputQps());
        return evaluationResult;
    }

    /**
     * 获取执行次数QPS
     * @return 执行次数QPS
     */
    @SuppressWarnings("WeakerAccess")
    public long getThroughputQps() {
        long configDuration = evaluationConfig.getConfigDuration();
        long configWarmUp = evaluationConfig.getConfigWarmUp();
        return (long)(((float)statisticsCalculator.getEvaluationCount() / ((float)configDuration - configWarmUp)) * 1000);
    }

    /**
     * 延迟校验
     * @param actualNs 实际时间(纳秒)
     * @param requiredMs 需求时间(毫秒)
     * @return {@code true} 是
     */
    private boolean validateLatency(float actualNs, float requiredMs) {
        long thresholdNs = (long)(requiredMs * MILLISECONDS.toNanos(1));
        return actualNs <= thresholdNs;
    }

    /**
     * 最小延迟是否符合
     * @return {@code true} 是
     */
    public boolean isMinAchieved() {
        if(evaluationRequire.getRequireMin() < 0) {
            return true;
        }
        return validateLatency(statisticsCalculator.getMinLatency(TimeUnit.NANOSECONDS), evaluationRequire.getRequireMin());
    }

    /**
     * 最大延迟是否符合
     * @return {@code true} 是
     */
    public boolean isMaxAchieved(){
        if(evaluationRequire.getRequireMax() < 0) {
            return true;
        }
        return validateLatency(statisticsCalculator.getMaxLatency(TimeUnit.NANOSECONDS), evaluationRequire.getRequireMax());
    }

    /**
     * 平均延迟是否符合
     * @return {@code true} 是
     */
    public boolean isAverageAchieved() {
        if(evaluationRequire.getRequireAverage() < 0) {
            return true;
        }
        return validateLatency(statisticsCalculator.getMeanLatency(TimeUnit.NANOSECONDS), evaluationRequire.getRequireAverage());
    }

    /**
     * 每秒执行次数是否符合
     * @return {@code true} 是
     */
    public boolean isTimesPerSecondAchieved() {
        return evaluationRequire.getRequireTimesPerSecond() < 0 || getThroughputQps() >= evaluationRequire.getRequireTimesPerSecond();
    }

    /**
     * 构建百分比是否通过 map 结果
     * @return
     */
    private Map<Integer, Boolean> buildIsPercentilesAchievedMap() {
        Map<Integer, Boolean> isPercentilesAchievedMap = newTreeMap();
        //1. 计算结果
        for(Map.Entry<Integer, Float> entry : evaluationRequire.getRequirePercentilesMap().entrySet()) {
            Integer percentile = entry.getKey();
            float thresholdMs = entry.getValue();   //限制的时间
            long thresholdNs = (long) (thresholdMs * MILLISECONDS.toNanos(1));    //
            boolean result = statisticsCalculator.getLatencyPercentile(percentile, NANOSECONDS) <= thresholdNs;
            isPercentilesAchievedMap.put(percentile, result);
        }
       return isPercentilesAchievedMap;
    }

    /**
     * 百分比阈值是否满足
     * @param isPercentilesAchievedMap 是否满足条件
     * @return {@code true} 是
     */
    private boolean isPercentilesAchieved(Map<Integer, Boolean> isPercentilesAchievedMap) {
        //校验是否通过
        for(Boolean bool : isPercentilesAchievedMap.values()) {
            if(!bool) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否成功
     * @param evaluationResult 构建结果
     * @return {@code true} 是
     */
    public boolean isSuccessful(EvaluationResult evaluationResult) {
        return evaluationResult.isMaxAchieved()
                && evaluationResult.isMinAchieved()
                && evaluationResult.isAverageAchieved()
                && evaluationResult.isTimesPerSecondAchieved()
                && evaluationResult.isPercentilesAchieved();
    }
    //endregion
}
