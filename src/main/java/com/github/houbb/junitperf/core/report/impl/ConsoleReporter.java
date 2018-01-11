package com.github.houbb.junitperf.core.report.impl;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;

import java.util.Set;

/**
 * 命令行报告
 * 描述：将统计结果输出到命令行。
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public class ConsoleReporter implements Reporter {

    @Override
    public String reportPath() {
        return null;
    }

    @Override
    public void report(Set<EvaluationContext> evaluationContextSet) {

    }

}
