package com.github.houbb.junitperf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对于性能测试来说，所有的要求都应该是消耗时间越低越好。
 * 这里基本是一些数学的统计概念：最大值、最小值、平均数等等。
 * 这些东西都是一个阈值的概念，可以进行抽象吗？
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JunitPerfRequire {

    /**
     * 最佳的运行耗时(单位：毫秒)
     * 1. 最快的运行耗时如果高于这个值，则视为失败
     * @return 最佳的运行耗时
     */
    long min() default 0L;

    /**
     * 最坏的运行耗时(单位：毫秒)
     * 1. 最坏的运行耗时如果高于这个值，则视为失败
     * @return 最坏的运行耗时
     */
    long max() default 0L;

    /**
     * 平均的运行耗时(单位：毫秒)
     * 1. 平均的运行耗时如果高于这个值，则视为失败
     * @return 平均的运行耗时
     */
    long average() default 0L;

    /**
     * 对于执行耗时的限定
     *
     * percentiles={"66:200", "96:500"}
     * 66% 的数据执行耗时不得超过 200ms;
     * 96% 的数据执行耗时不得超过 500ms;
     * @return 执行耗时界定的数组
     */
    String[] percentiles() default {};

    /**
     * 每秒的最小执行次数
     * 1. 如果低于这个最小执行次数，则视为失败
     * @return 每秒的最小执行次数
     */
    int timesPerSecond() default 0;

}
