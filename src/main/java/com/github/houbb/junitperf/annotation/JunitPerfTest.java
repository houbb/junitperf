package com.github.houbb.junitperf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 执行接口
 * 对于每一个测试方法的条件限制
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JunitPerfTest {

    /**
     * 测试执行次数
     * 默认执行一次
     * @return int val
     */
    int times() default 1;

    /**
     * 执行时使用多少线程执行
     * @return int val
     */
    int threads() default 1;

    /**
     * 准备时间（单位：毫秒）
     * @return time in mills
     */
    long warmUp() default 0L;

    /**
     * 执行时间。（单位：毫秒）
     * 这里的执行时间不包含准备时间。
     * @return time in mills
     */
    long duration() default 0L;

}
