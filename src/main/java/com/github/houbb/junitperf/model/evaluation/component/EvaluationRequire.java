package com.github.houbb.junitperf.model.evaluation.component;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.model.BaseModel;

import org.apiguardian.api.API;

import java.util.Map;

/**
 * 验证需求
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class EvaluationRequire extends BaseModel {

    private static final long   serialVersionUID = 377391606016334079L;

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

    public float getRequireMin() {
        return requireMin;
    }

    public void setRequireMin(float requireMin) {
        this.requireMin = requireMin;
    }

    public float getRequireMax() {
        return requireMax;
    }

    public void setRequireMax(float requireMax) {
        this.requireMax = requireMax;
    }

    public float getRequireAverage() {
        return requireAverage;
    }

    public void setRequireAverage(float requireAverage) {
        this.requireAverage = requireAverage;
    }

    public int getRequireTimesPerSecond() {
        return requireTimesPerSecond;
    }

    public void setRequireTimesPerSecond(int requireTimesPerSecond) {
        this.requireTimesPerSecond = requireTimesPerSecond;
    }

    public Map<Integer, Float> getRequirePercentilesMap() {
        return requirePercentilesMap;
    }

    public void setRequirePercentilesMap(Map<Integer, Float> requirePercentilesMap) {
        this.requirePercentilesMap = requirePercentilesMap;
    }
}
