package com.github.houbb.junitperf.examples;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/7/2 上午10:59  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since 2.0.6
 */
public class HelloWorldMultiTest {

    @JunitPerfConfig(duration = 1000)
    public void helloTest() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000)
    public void helloTest2() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

}
