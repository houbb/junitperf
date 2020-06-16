package com.github.houbb.junitperf.examples;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;

/**
 * <p> 方法名字较长的情况 </p>
 *
 * <pre> Created: 2018/7/2 上午10:59  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0
 * @since 2.0.6
 */
public class LongNameHtmlTest {

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong2() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong3() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong4() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong5() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong6() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong7() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong8() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

}
