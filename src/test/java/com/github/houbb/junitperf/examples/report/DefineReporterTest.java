package com.github.houbb.junitperf.examples.report;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import com.github.houbb.junitperf.examples.report.support.MyReporter;
import org.junit.Rule;
import org.junit.Test;

/**
 * 将性能测试内容以自定义的方式输出
 *
 * 备注：用户在使用时可以自行定义，配合组合报告的方式达到自己的目的。
 * @see MultiReporterTest 组合报告
 * @author houbinbin
 * @version 1.0.0
 * @since 1.0.0, 2018/01/14
 */
public class DefineReporterTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new MyReporter());

    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    public void helloWorldTest() throws InterruptedException {
        Thread.sleep(200);
    }

}
