package com.github.houbb.junitperf.examples.report;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 将性能测试内容输出到多个位置
 * @author houbinbin
 * @version 1.0.0
 * @since 1.0.0, 2018/01/14
 */
public class MultiReporterTest {

    /**
     * 同时输出到命令行和HTML
     */
    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new HtmlReporter(), new ConsoleReporter());

    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    public void helloWorldTest() throws InterruptedException {
        Thread.sleep(200);
    }

}
