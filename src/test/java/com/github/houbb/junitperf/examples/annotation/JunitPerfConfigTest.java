package com.github.houbb.junitperf.examples.annotation;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * 2018/1/14
 *
 * @author houbinbin
 * @version 1.0
 * @since 1.7
 */
public class JunitPerfConfigTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();

    /**
     * 2个线程运行。
     * 准备时间：1000ms
     * 运行时间: 2000ms
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(threads = 2, warmUp = 1000, duration = 2000)
    public void junitPerfConfigTest() throws InterruptedException {
        System.out.println("junitPerfConfigTest");
        Thread.sleep(200);
    }

}
