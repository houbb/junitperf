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
     * 生成报告的文件路径
     * @return
     */
    String reportPath();


    /**
     * 用于生成报告
     * @param evaluationContextSet 评价集合
     */
    void report(Set<EvaluationContext> evaluationContextSet);

}
