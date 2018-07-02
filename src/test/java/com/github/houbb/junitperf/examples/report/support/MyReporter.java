package com.github.houbb.junitperf.examples.report.support;

import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.model.evaluation.EvaluationContext;

import java.util.Set;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/7/2 上午11:05  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class MyReporter implements Reporter {
    @Override
    public void report(Class testClass, Set<EvaluationContext> evaluationContextSet) {
        System.out.println("MyReporter");
    }
}
