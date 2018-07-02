package com.github.houbb.junitperf.examples.report;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.examples.report.support.MyReporter;

/**
 * <p> 自定义输出 </p>
 *
 * <pre> Created: 2018/7/2 上午11:04  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since JDK 1.7
 */
public class DefineReporterTest {

    @JunitPerfConfig(duration = 1000, reporter = MyReporter.class)
    public void helloTest() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

}
