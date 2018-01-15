package com.github.houbb.junitperf.examples.cases;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 所有限定失败测试
 *
 * @author houbinbin
 * @version 1.0.1
 * @since 1.0.1, 2018/01/15
 */
public class AllRequireFailTest {


    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new HtmlReporter());

    /**
     * 性能测试失败场景
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 10, percentiles = {"20:220", "50:230"})
    public void helloWorldTest() throws InterruptedException {
        Thread.sleep(400);
    }

}
