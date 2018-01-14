package com.github.houbb.junitperf.examples;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

public class HelloTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();

    @Test
    @JunitPerfConfig(threads = 2, warmUp = 500, duration = 1000)
    @JunitPerfRequire(percentiles = {"20:210"})
    public void helloWorldTest() throws InterruptedException {
        System.out.println("hello world");
        Thread.sleep(20);
    }

}
