package com.github.houbb.junitperf.model.evaluation;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationConfig;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationRequire;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationResult;
import com.github.houbb.junitperf.support.builder.EvaluationConfigBuilder;
import com.github.houbb.junitperf.support.builder.EvaluationRequireBuilder;
import com.github.houbb.junitperf.support.builder.EvaluationResultBuilder;
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

    /**
     * 统计者
     */
    private StatisticsCalculator statisticsCalculator;

    /**
     * 配置
     */
    private EvaluationConfig evaluationConfig;

    /**
     * 限定
     */
    private EvaluationRequire evaluationRequire;

    /**
     * 结果
     */
    private EvaluationResult evaluationResult;


    public EvaluationContext(String methodName, String startTime) {
        this.methodName = methodName;
        this.startTime = startTime;
    }

    /**
     * 加载配置
     * @param junitPerfConfig 配置注解
     */
    public void loadConfig(JunitPerfConfig junitPerfConfig) {
        this.evaluationConfig = new EvaluationConfigBuilder(junitPerfConfig).build();
    }

    /**
     * 加载评判标准
     * @param junitPerfRequire 评判注解
     */
    public void loadRequire(JunitPerfRequire junitPerfRequire) {
        this.evaluationRequire = new EvaluationRequireBuilder(junitPerfRequire).build();
    }

    /**
     * 运行校验
     * 1. 必须保证统计这一步已经做完了
     */
    public void runValidation() {
        evaluationResult = new EvaluationResultBuilder(evaluationConfig, evaluationRequire, statisticsCalculator).build();
    }

    //region getter & setter
    public String getMethodName() {
        return methodName;
    }

    public String getStartTime() {
        return startTime;
    }

    public StatisticsCalculator getStatisticsCalculator() {
        return statisticsCalculator;
    }

    public void setStatisticsCalculator(StatisticsCalculator statisticsCalculator) {
        this.statisticsCalculator = statisticsCalculator;
    }

    public EvaluationConfig getEvaluationConfig() {
        return evaluationConfig;
    }

    public void setEvaluationConfig(EvaluationConfig evaluationConfig) {
        this.evaluationConfig = evaluationConfig;
    }

    public EvaluationRequire getEvaluationRequire() {
        return evaluationRequire;
    }

    public void setEvaluationRequire(EvaluationRequire evaluationRequire) {
        this.evaluationRequire = evaluationRequire;
    }

    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    public void setEvaluationResult(EvaluationResult evaluationResult) {
        this.evaluationResult = evaluationResult;
    }
    //endregion
}
