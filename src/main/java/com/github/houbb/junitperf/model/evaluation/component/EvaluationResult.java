package com.github.houbb.junitperf.model.evaluation.component;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.model.BaseModel;

import org.apiguardian.api.API;

import java.util.Map;

/**
 * 验证结果
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class EvaluationResult extends BaseModel {

    private static final long serialVersionUID = 3402389144055056153L;

    /**
     * 运行速度 QPS
     */
    private long throughputQps;

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
     * 百分比测试结果
     */
    private Map<Integer, Boolean> isPercentilesAchievedMap;

    /**
     * 验证是否成功
     * 备注：当所有的校验通过则视为通过
     */
    private boolean isSuccessful;

    public long getThroughputQps() {
        return throughputQps;
    }

    public void setThroughputQps(long throughputQps) {
        this.throughputQps = throughputQps;
    }

    public boolean isMinAchieved() {
        return isMinAchieved;
    }

    public void setMinAchieved(boolean minAchieved) {
        isMinAchieved = minAchieved;
    }

    public boolean isMaxAchieved() {
        return isMaxAchieved;
    }

    public void setMaxAchieved(boolean maxAchieved) {
        isMaxAchieved = maxAchieved;
    }

    public boolean isAverageAchieved() {
        return isAverageAchieved;
    }

    public void setAverageAchieved(boolean averageAchieved) {
        isAverageAchieved = averageAchieved;
    }

    public boolean isTimesPerSecondAchieved() {
        return isTimesPerSecondAchieved;
    }

    public void setTimesPerSecondAchieved(boolean timesPerSecondAchieved) {
        isTimesPerSecondAchieved = timesPerSecondAchieved;
    }

    public boolean isPercentilesAchieved() {
        return isPercentilesAchieved;
    }

    public void setPercentilesAchieved(boolean percentilesAchieved) {
        isPercentilesAchieved = percentilesAchieved;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public Map<Integer, Boolean> getIsPercentilesAchievedMap() {
        return isPercentilesAchievedMap;
    }

    public void setIsPercentilesAchievedMap(Map<Integer, Boolean> isPercentilesAchievedMap) {
        this.isPercentilesAchievedMap = isPercentilesAchievedMap;
    }

}
