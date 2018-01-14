package com.github.houbb.junitperf.examples.report;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 将性能测试内容输出到-HTML
 * HTML默认位置：${BASE_DIR}/target/junitperf/reports/junitperf-report.html
 * @author houbinbin
 * @version 1.0.0
 * @since 1.0.0, 2018/01/14
 */
public class HtmlReporterTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new HtmlReporter());

    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    public void helloWorldTest() throws InterruptedException {
        Thread.sleep(200);
    }

}
