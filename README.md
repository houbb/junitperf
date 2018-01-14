# junitperf

Junit 性能测试框架，对于 Junit 的框架拓展。


# Why use?

- 可以和 Junit 完美契合

- 使用简单，便于项目开发过程中的测试实用

- 提供拓展，用户可进行自定义开发。 


# 快速开始

## 项目依赖

- jdk

jdk1.7 及其以上版本

- junit

Junit4 及其以上版本

## Hello World

[入门案例地址](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/HelloWorldTest.java)

- jar 包引入

```xml
 <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>

<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>junitperf</artifactId>
    <version>1.0.0</version>
</dependency>
```

- 使用例子

```java
/**
 * 入门测试
 *
 * @author houbinbin
 * @version 1.0.0
 * @since 1.0.0, 2018/01/14
 */
public class HelloWorldTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule();

    /**
     * 单一线程，执行 1000ms，默认以 html 输出测试结果
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(duration = 1000)
    public void helloWorldTest() throws InterruptedException {
        //This is what you want to test.
        System.out.println("hello world");
        Thread.sleep(20);
    }
}
```

# 配置说明

## 声明 Rule

```java
@Rule
public JunitPerfRule junitPerfRule = new JunitPerfRule();
```
这里主要是对于性能测试统计的**输出方式**。
支持以下方式：

| 方式 | 案例 |
|:----|:----|
| 默认方式 | [DefaultReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/DefaultReporterTest.java) |
| 命令行 | [ConsoleReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/ConsoleReporterTest.java) |
| HTML | [HtmlReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/HtmlReporterTest.java) |
| 组合方式 | [MultiReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/MultiReporterTest.java) |
| 自定义方式 | [DefineReporterTest](https://github.com/houbb/junitperf/blob/master/src/test/java/com/github/houbb/junitperf/examples/report/DefineReporterTest.java) |


## 测试注解指定

### @JunitPerfConfig

指定测试时的属性配置。(必填项)

| 属性 | 说明 | 类型 | 默认值 | 备注 |
|:----|:----|:----|:----|:----|
| threads | 执行时使用多少线程执行 | int | 1 | |
| warmUp | 准备时间 | long | 0 | 单位：毫秒 |
| duration | 执行时间 | long | 60_000(1分钟) | 单位：毫秒 |


使用如下：

```java
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
```

### @JunitPerfRequire

指定测试时需要达到的要求。(选填项)

| 属性 | 说明 | 类型 | 默认值 | 备注 |
|:----|:----|:----|:----|:----|
| min | 最佳的运行耗时 | float | -1 | 最快的运行耗时如果高于这个值，则视为失败。单位：毫秒 |
| max | 平均的运行耗时 | float | -1 | 最坏的运行耗时如果高于这个值，则视为失败。单位：毫秒 |
| average | 平均的运行耗时 | float | -1 | 平均的运行耗时如果高于这个值，则视为失败。单位：毫秒 |
| timesPerSecond | 每秒的最小执行次数 | int | 0 | 如果低于这个最小执行次数，则视为失败。 |
| percentiles | 对于执行耗时的限定 | String[] | {} | percentiles={"20:220", "30:250"}。20% 的数据执行耗时不得超过 220ms;30% 的数据执行耗时不得超过 250ms; |

使用如下：

```java
public class JunitPerfRequireTest {

    @Rule
    public JunitPerfRule junitPerfRule = new JunitPerfRule(new ConsoleReporter());

    /**
     * 配置：2个线程运行。准备时间：1000ms。运行时间: 2000ms。
     * 要求：最快不可低于 210ms, 最慢不得低于 250ms, 平均不得低于 225ms, 每秒运行次数不得低于 4 次。
     * 20% 的数据不低于 220ms, 50% 的数据不得低于 230ms;
     *
     * @throws InterruptedException if any
     */
    @Test
    @JunitPerfConfig(threads = 2, warmUp = 1000, duration = 2000)
    @JunitPerfRequire(min = 210, max = 250, average = 225, timesPerSecond = 4, percentiles = {"20:220", "50:230"})
    public void junitPerfConfigTest() throws InterruptedException {
        System.out.println("junitPerfConfigTest");
        Thread.sleep(200);
    }

}
```




