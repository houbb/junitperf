# junitperf

> [使用说明](README.md)

[junitperf](https://github.com/houbb/junitperf) is a performance testing framework designed for java developers.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/junitperf/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/junitperf)
[![Build Status](https://www.travis-ci.org/houbb/junitperf.svg)](https://www.travis-ci.org/houbb/junitperf)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/junitperf/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/junitperf)

## Why use it?

- It fits perfectly with Junit5.

- Simple to use, convenient for practical testing during project development.

- Provide expansion, users can customize development.

## Features

- Support I18N

- Support multiple report generation methods, support custom

- Junt5 perfect support, easy for Java developers to use

## CHANGELOG

[CHANGELOG](CHANGELOG.md)

### v2.0.4 major change

1. remove log-integration dependency

2. doc optimize

# Quick Start

## Required

- jdk1.8 or later

- [Junit5](https://junit.org/junit5/) 

## maven 

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>junitperf</artifactId>
    <version>2.0.4</version>
</dependency>
```

## hello world

> [hello world](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/HelloWorldTest.java)

- demo

```java
public class HelloWorldTest {

    @JunitPerfConfig(duration = 1000)
    public void helloTest() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Hello Junit5");
    }

}
```

# Config

## Annotation

### @JunitPerfConfig

config for test

| Attr | Desc | Type | Default | Remark |
|:----|:----|:----|:----|:----|
| threads | How many threads are used to execute | int | 1 | |
| warmUp | Preparation time | long | 0 | Unit：mills |
| duration | Execution time | long | 60_000(1 min) | Unit：mills |
| statistics | Statistics impl | StatisticsCalculator | DefaultStatisticsCalculator |  |
| reporter | Reporter impl | Reporter | ConsoleReporter |  |

as following：

```java
public class JunitPerfConfigTest {

    /**
     * two threads
     * Preparation time：1000ms
     * Execution time: 2000ms
     * @throws InterruptedException if any
     */
    @JunitPerfConfig(threads = 2, warmUp = 1000, duration = 2000)
    public void junitPerfConfigTest() throws InterruptedException {
        System.out.println("junitPerfConfigTest");
        Thread.sleep(200);
    }

}
```

### Reporter types

This is mainly the **output method** for performance test statistics.

The following methods are supported:

| TYPE | DEMO |
|:----|:----|
| Default | [DefaultReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/DefaultReporterTest.java) |
| Console | [ConsoleReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/ConsoleReporterTest.java) |
| HTML | [HtmlReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/HtmlReporterTest.java) |
| Multi | [MultiReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/MultiReporterTest.java) |
| Define | [DefineReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/DefineReporterTest.java) |

### @JunitPerfRequire

指定测试时需要达到的要求。(选填项)

| Properties | Description | Type | Default | Remarks |
|:----|:----|:----|:----|:----|
| min | best cost | float | -1 | If the fastest running time is higher than this value, it is regarded as a failure. Unit: ms |
| max | worst cost | float | -1 | If the worst running time is higher than this value, it is regarded as failure. Unit: ms |
| average | avg cost | float | -1 | If the average running time is higher than this value, it is regarded as a failure. Unit: ms |
| timesPerSecond | The minimum number of executions per second | int | 0 | If it is lower than this minimum number of executions, it is regarded as a failure. |
| percentiles | Limitation on execution time | String[] | {} | percentiles={"20:220", "30:250"}。20% of the data execution time should not exceed 220ms; 30% of the data execution time should not exceed 250ms; |

as following：

```java
public class JunitPerfRequireTest {
    /**
     * Configuration: 2 threads running. Preparation time: 1000ms. Running time: 2000ms.
     * Requirements: The fastest should not be less than 210ms, the slowest should not be less than 250ms, the average should not be less than 225ms, and the number of operations per second should not be less than 4 times.
     * 20% of the data is not less than 220ms, and 50% of the data is not less than 230ms;
     *
     * @throws InterruptedException if any
     */
    @JunitPerfConfig(threads = 2, warmUp = 1000, duration = 2000)
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    public void junitPerfConfigTest() throws InterruptedException {
        System.out.println("junitPerfConfigTest");
        Thread.sleep(200);
    }

}
```

## Reporting method

### Command line

Roughly as follows:

```
[INFO] 2018-01-14 22:16:31.419 [] - Started at:   2018-01-14 22:16:30.194
[INFO] 2018-01-14 22:16:31.419 [] - Invocations:  10
[INFO] 2018-01-14 22:16:31.420 [] - Success:  10
[INFO] 2018-01-14 22:16:31.420 [] - Errors:   0
[INFO] 2018-01-14 22:16:31.420 [] - Thread Count: 2
[INFO] 2018-01-14 22:16:31.421 [] - Warm up:      0ms
[INFO] 2018-01-14 22:16:31.421 [] - Execution time: 1000ms
[INFO] 2018-01-14 22:16:31.421 [] - Throughput:     10/s (Required: -1/s) - PASSED
[INFO] 2018-01-14 22:16:31.424 [] - Min latency:   200.2112ms (Required: -1.0ms) - PASSED
[INFO] 2018-01-14 22:16:31.424 [] - Max latency:    205.67862ms (Required: -1.0ms) - PASSED
[INFO] 2018-01-14 22:16:31.425 [] - Ave latency:    202.97829ms (Required: -1.0ms) - PASSED
```

### HTML way

The page is as follows:

Later, style adjustments will be made.

![junitperf-report-html.png](doc/img/junitperf-report-html.png)

# Relative Open-Source Framework

[data-factory](https://github.com/houbb/data-factory): Automatically generate test data

[gen-test-plugin](https://github.com/houbb/gen-test-plugin): Maven plugin to automatically generate test cases

# Road-MAP

- [ ] Memory usage statistics