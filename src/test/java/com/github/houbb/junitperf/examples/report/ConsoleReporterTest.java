package com.github.houbb.junitperf.examples.report;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

public class ConsoleReporterTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new ConsoleReporter());

    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    @JunitPerfRequire(percentiles = {"1:220"})
    public void helloWorldTest() throws InterruptedException {
        Thread.sleep(200);
    }

}
