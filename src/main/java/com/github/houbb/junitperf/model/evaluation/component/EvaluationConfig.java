package com.github.houbb.junitperf.model.evaluation.component;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.model.BaseModel;

import org.apiguardian.api.API;

/**
 * 验证配置
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public class EvaluationConfig extends BaseModel {

    private static final long serialVersionUID = 3584449169952751834L;

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

    public int getConfigThreads() {
        return configThreads;
    }

    public void setConfigThreads(int configThreads) {
        this.configThreads = configThreads;
    }

    public long getConfigWarmUp() {
        return configWarmUp;
    }

    public void setConfigWarmUp(long configWarmUp) {
        this.configWarmUp = configWarmUp;
    }

    public long getConfigDuration() {
        return configDuration;
    }

    public void setConfigDuration(long configDuration) {
        this.configDuration = configDuration;
    }
}
