package com.github.houbb.junitperf.model.evaluation;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationConfig;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationRequire;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationResult;
import com.github.houbb.junitperf.support.builder.EvaluationConfigBuilder;
import com.github.houbb.junitperf.support.builder.EvaluationRequireBuilder;
import com.github.houbb.junitperf.support.builder.EvaluationResultBuilder;

import org.apiguardian.api.API;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 评价接口定义，用于展现最后的性能评价结果。
 * 所有的结果直接继承此接口，可用于生成对应的报告信息。
 * 备注：我觉得这部分的代码写的不合理，应该拆分为贫血模型。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class EvaluationContext implements Serializable {

    private static final long serialVersionUID = -3314188451986878388L;

    /**
     * 测试实例
     */
    private final Object testInstance;

    /**
     * 测试方法
     */
    private final Method testMethod;

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


    public EvaluationContext(final Object testInstance,
                             final Method testMethod,
                             String startTime) {
        this.testInstance = testInstance;
        this.testMethod = testMethod;
        this.methodName = testMethod.getName();
        this.startTime = startTime;
    }

    /**
     * 加载配置
     * @param junitPerfConfig 配置注解
     */
    public synchronized void loadConfig(JunitPerfConfig junitPerfConfig) {
        this.evaluationConfig = new EvaluationConfigBuilder(junitPerfConfig).build();
    }

    /**
     * 加载评判标准
     * @param junitPerfRequire 评判注解
     */
    public synchronized void loadRequire(JunitPerfRequire junitPerfRequire) {
        this.evaluationRequire = new EvaluationRequireBuilder(junitPerfRequire).build();
    }

    /**
     * 运行校验
     * 1. 必须保证统计这一步已经做完了
     */
    public void runValidation() {
        evaluationResult = new EvaluationResultBuilder(evaluationConfig, evaluationRequire, statisticsCalculator).build();
    }

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

    public EvaluationRequire getEvaluationRequire() {
        return evaluationRequire;
    }

    public EvaluationResult getEvaluationResult() {
        return evaluationResult;
    }

    public Object getTestInstance() {
        return testInstance;
    }

    public Method getTestMethod() {
        return testMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EvaluationContext that = (EvaluationContext) o;

        if (methodName != null ? !methodName.equals(that.methodName) : that.methodName != null) {
            return false;
        }
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) {
            return false;
        }
        if (statisticsCalculator != null ? !statisticsCalculator.equals(that.statisticsCalculator) : that.statisticsCalculator != null) {
            return false;
        }
        if (evaluationConfig != null ? !evaluationConfig.equals(that.evaluationConfig) : that.evaluationConfig != null) {
            return false;
        }
        if (evaluationRequire != null ? !evaluationRequire.equals(that.evaluationRequire) : that.evaluationRequire != null) {
            return false;
        }
        return evaluationResult != null ? evaluationResult.equals(that.evaluationResult) : that.evaluationResult == null;
    }

    @Override
    public int hashCode() {
        int result = methodName != null ? methodName.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (statisticsCalculator != null ? statisticsCalculator.hashCode() : 0);
        result = 31 * result + (evaluationConfig != null ? evaluationConfig.hashCode() : 0);
        result = 31 * result + (evaluationRequire != null ? evaluationRequire.hashCode() : 0);
        result = 31 * result + (evaluationResult != null ? evaluationResult.hashCode() : 0);
        return result;
    }
}
