package com.github.houbb.junitperf.core.annotation;

import com.github.houbb.junitperf.constant.VersionConstant;
import com.github.houbb.junitperf.core.jupiter.provider.PerfConfigProvider;
import com.github.houbb.junitperf.core.report.Reporter;
import com.github.houbb.junitperf.core.report.impl.ConsoleReporter;
import com.github.houbb.junitperf.core.statistics.StatisticsCalculator;
import com.github.houbb.junitperf.core.statistics.impl.DefaultStatisticsCalculator;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * 执行接口
 * 对于每一个测试方法的条件配置
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Documented
@API(status = API.Status.MAINTAINED, since = VersionConstant.V2_0_0)
@ExtendWith(PerfConfigProvider.class)
@TestTemplate
public @interface JunitPerfConfig {

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
     * 默认值：默认为 1min
     * 这里的执行时间不包含准备时间。
     * @return time in mills
     */
    long duration() default 60_000L;

    /**
     * 存放统计信息工具
     * @return 统计实现类
     */
    Class<? extends StatisticsCalculator> statistics() default DefaultStatisticsCalculator.class;

    /**
     * 存放报告信息类
     * @return 报告信息
     */
    Class<? extends Reporter>[] reporter() default {ConsoleReporter.class};

}
