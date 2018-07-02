package com.github.houbb.junitperf;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.annotation.JunitPerfRequire;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;

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

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void helloTest() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000)
    @JunitPerfRequire(max = 80)
    public void helloTwoTest() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("oho");
    }

}
