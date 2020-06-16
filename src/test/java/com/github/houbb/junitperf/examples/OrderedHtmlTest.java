package com.github.houbb.junitperf.examples;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * <p> 按照顺序执行 </p>
 *
 * <pre> Created: 2018/7/2 上午10:59  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-execution-order
 *
 * @author houbinbin
 * @version 1.0
 * @since 2.0.6
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderedHtmlTest {

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    @Order(1)
    public void myNameIsLongLongLongLong() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    @Order(2)
    public void myNameIsLongLongLongLong2() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    @Order(3)
    public void myNameIsLongLongLongLong3() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    @Order(4)
    public void myNameIsLongLongLongLong4() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @Order(5)
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong5() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @Order(6)
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong6() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @Order(7)
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong7() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

    @Order(8)
    @JunitPerfConfig(duration = 1000, reporter = {HtmlReporter.class})
    public void myNameIsLongLongLongLong8() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

}
