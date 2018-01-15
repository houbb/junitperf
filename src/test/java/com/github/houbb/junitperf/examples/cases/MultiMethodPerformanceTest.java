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
public class MultiMethodPerformanceTest {


    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new HtmlReporter());

    /**
     * 性能测试失败场景
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(threads = 2, duration = 1000)
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 10, percentiles = {"20:220", "50:230"})
    public void failPerformanceTest() throws InterruptedException {
        Thread.sleep(400);
    }

    /**
     * 性能测试成功场景
     * 配置：2个线程运行。准备时间：1000ms。运行时间: 2000ms。
     * 要求：最快不可低于 210ms, 最慢不得低于 250ms, 平均不得低于 225ms, 每秒运行次数不得低于 4 次。
     * 20% 的数据不低于 220ms, 50% 的数据不得低于 230ms;
     *
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(threads = 2, warmUp = 1000, duration = 2000)
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    public void successPerformanceTest() throws InterruptedException {
        Thread.sleep(200);
    }

}
