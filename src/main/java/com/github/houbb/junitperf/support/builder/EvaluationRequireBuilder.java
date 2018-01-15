package com.github.houbb.junitperf.support.builder;

import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.model.evaluation.component.EvaluationRequire;
import com.github.houbb.paradise.common.support.builder.Builder;
import com.github.houbb.paradise.common.util.ArrayUtil;
import com.github.houbb.paradise.common.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * 验证需求-构建者
 * @author bbhou
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
public class EvaluationRequireBuilder implements Builder<EvaluationRequire> {

    private final JunitPerfRequire junitPerfRequire;

    public EvaluationRequireBuilder(JunitPerfRequire junitPerfRequire) {
        this.junitPerfRequire = junitPerfRequire;
    }

    @Override
    public EvaluationRequire build() {
        EvaluationRequire evaluationRequire = new EvaluationRequire();

        if(ObjectUtil.isNotNull(junitPerfRequire)) {
            validateJunitPerfRequire(junitPerfRequire);

            evaluationRequire.setRequireMin(junitPerfRequire.min());
            evaluationRequire.setRequireMax(junitPerfRequire.max());
            evaluationRequire.setRequireAverage(junitPerfRequire.average());
            evaluationRequire.setRequireTimesPerSecond(junitPerfRequire.timesPerSecond());
            evaluationRequire.setRequirePercentilesMap(parseRequirePercentilesMap(junitPerfRequire.percentiles()));
        } else {
            evaluationRequire.setRequireMin(-1);
            evaluationRequire.setRequireMax(-1);
            evaluationRequire.setRequireAverage(-1);
            evaluationRequire.setRequireTimesPerSecond(-1);
            evaluationRequire.setRequirePercentilesMap(Maps.<Integer, Float>newHashMap());  //避免NPE
        }
        return evaluationRequire;
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
}
