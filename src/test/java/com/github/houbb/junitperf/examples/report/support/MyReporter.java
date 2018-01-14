package com.github.houbb.junitperf.examples.report.support;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;

import java.util.Set;

/**
 * 自定义报告输出方式
 *
 * @author houbinbin
 * @version 1.0.0
 * @since 1.0.0, 2018/01/14
 */
public class MyReporter implements Reporter {

    @Override
    public void report(Class testClass, Set<EvaluationContext> evaluationContextSet) {
        System.out.println("My reporter...");
    }

}
