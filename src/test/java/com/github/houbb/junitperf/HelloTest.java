package com.github.houbb.junitperf;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;

/**
 * <p> </p>
 *
 * <pre> Created: 2018/6/28 下午5:24  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class HelloTest {

    @JunitPerfConfig(duration = 1000)
    public void helloTest() {
        System.out.println("Hello Junit5");
    }

}
