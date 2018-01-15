package com.github.houbb.junitperf.support.builder;


import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationConfig;
import com.github.houbb.paradise.common.support.builder.Builder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 验证配置-构建者
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
public class EvaluationConfigBuilder implements Builder<EvaluationConfig> {

    private final JunitPerfConfig junitPerfConfig;

    public EvaluationConfigBuilder(JunitPerfConfig junitPerfConfig) {
        this.junitPerfConfig = junitPerfConfig;
    }

    @Override
    public EvaluationConfig build() {
        validateJunitPerfConfig(junitPerfConfig);

        EvaluationConfig evaluationConfig = new EvaluationConfig();
        evaluationConfig.setConfigThreads(junitPerfConfig.threads());
        evaluationConfig.setConfigWarmUp(junitPerfConfig.warmUp());
        evaluationConfig.setConfigDuration(junitPerfConfig.duration());
        return evaluationConfig;
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
}
