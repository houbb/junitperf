package com.github.houbb.junitperf.examples;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.rule.JunitPerfRule;

import org.junit.Rule;
import org.junit.Test;


/**
 * 入门测试-有成功+失败
 *
 * @author houbinbin
 * @version 1.0.2
 * @since 1.0.2, 2018/05/05
 */
public class HelloWorldMixTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();

    /**
     * 单一线程，执行 1000ms，默认以 html 输出测试结果
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(duration = 1000)
    public void helloWorldTest() throws InterruptedException {
        System.out.println("hello world");
        Thread.sleep(20);
    }

    /**
     * 单一线程，执行 1000ms，默认以 html 输出测试结果
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(duration = 1000)
    @JunitPerfRequire(min = 1, max = 100)
    public void helloWorld2Test() throws InterruptedException {
        System.out.println("hello world2");
        Thread.sleep(500);
    }

}
