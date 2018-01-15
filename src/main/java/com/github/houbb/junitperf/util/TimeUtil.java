package com.github.houbb.junitperf.util;

import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/11
 */
public final class TimeUtil {


    /**
     * 毫秒转化为纳秒
     * 1. 如果时间格式小于0，则视为0
     * @param ms 毫秒
     * @return 纳秒
     * @since 1.0.1, 2018/01/15 命名修正
     */
    public static long convertMsToNs(long ms) {
        return TimeUnit.NANOSECONDS.convert(ms > 0 ? ms : 0, TimeUnit.MILLISECONDS);
    }

}
