/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright (c) 2012-2018. houbinbin Inc.
 * word-checker All rights reserved.
 */

package com.github.houbb.junitperf.support.i18n;

import com.github.houbb.junitperf.model.vo.I18nVo;

import org.apiguardian.api.API;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <p> i18n 对象 </p>
 *
 * <pre> Created: 2018-05-02 11:24  </pre>
 * <pre> Project: junitperf  </pre>
 *
 * @author houbinbin
 * @version 1.0.2
 * @since 1.0.2
 */
@API(status = API.Status.INTERNAL)
public class I18N {

    private static final String DEFAULT_PROPERTIES_FILE_NAME = "i18n.JunitPerfMessages";

    public static String get(final String key) {
        Locale currentLocale = Locale.getDefault();
        ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_PROPERTIES_FILE_NAME, currentLocale);
        return myResources.getString(key);
    }

    public static class Key {
        public static final String memory = "memory";
        public static final String warm_up = "warm_up";
        public static final String max_latency = "max_latency";
        public static final String min_latency = "min_latency";
        public static final String thread_count = "thread_count";
        public static final String report_created_by = "report_created_by";
        public static final String started_at = "started_at";
        public static final String required = "required";
        public static final String junit_performance_report = "junit_performance_report";
        public static final String type = "type";
        public static final String top = "top";
        public static final String throughput = "throughput";
        public static final String avg_latency = "avg_latency";
        public static final String invocations = "invocations";
        public static final String execution_time = "execution_time";
        public static final String success = "success";
        public static final String actual = "actual";

        /**
         * 报告信息为空
         */
        public static final String reportIsEmpty="reportIsEmpty";
    }

    /**
     * 构建 18n 对象
     *
     * ps: 这里其实可以用反射重新构建，简化代码
     * @return 对象
     */
    public static I18nVo buildI18nVo() {
        I18nVo vo = new I18nVo();
        vo.setJunit_performance_report(get(Key.junit_performance_report));
        vo.setTop(get(Key.top));
        vo.setReport_created_by(get(Key.report_created_by));

        vo.setWarm_up(get(Key.warm_up));
        vo.setStarted_at(get(Key.started_at));
        vo.setExecution_time(get(Key.execution_time));
        vo.setInvocations(get(Key.invocations));
        vo.setThread_count(get(Key.thread_count));
        vo.setSuccess(get(Key.success));

        vo.setType(get(Key.type));
        vo.setActual(get(Key.actual));
        vo.setRequired(get(Key.required));
        vo.setThroughput(get(Key.throughput));
        vo.setMax_latency(get(Key.max_latency));
        vo.setMin_latency(get(Key.min_latency));
        vo.setAvg_latency(get(Key.avg_latency));
        vo.setMemory(get(Key.memory));
        return vo;
    }

}
