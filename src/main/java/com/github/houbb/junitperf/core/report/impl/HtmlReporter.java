package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;

import java.util.Set;

/**
 * 网页报告
 * 描述：将统计结果输出HTML页面，打算将此种方式作为默认的报告方式。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class HtmlReporter implements Reporter {

    @Override
    public String reportPath() {
        return null;
    }

    @Override
    public void report(Set<EvaluationContext> evaluationContextSet) {
        System.out.println("HtmlReporter gen..");
    }

}
