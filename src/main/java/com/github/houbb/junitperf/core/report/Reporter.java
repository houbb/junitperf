package com.github.houbb.junitperf.core.report;


import com.github.houbb.junitperf.model.evaluation.EvaluationContext;

import java.util.Set;

/**
 * 报告接口
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public interface Reporter {

    /**
     * 用于生成报告
     * @param testClass 测试类 class 相关信息
     * @param evaluationContextSet 评价集合
     */
    void report(Class testClass, Set<EvaluationContext> evaluationContextSet);

}
