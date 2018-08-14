/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright (c) 2012-2018. houbinbin Inc.
 * markdown-toc All rights reserved.
 */

package com.github.houbb.junitperf.util;

import org.apiguardian.api.API;

/**
 * 线程 工具类
 * @author bbhou
 * @since 2.0.1, 2018-08-11 00:17:08
 * @version 2.0.2
 */
@API(status = API.Status.MAINTAINED)
public final class ThreadUtil {

    /**
     * 获取 cpu 数量
     * @return cpu 数量
     */
    private static int cpuNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 最佳线程数量
     * @return 线程数量
     */
    public static int bestThreadNum() {
        int cpuNum = cpuNum();
        return cpuNum * 3;
    }

    /**
     * 最佳线程数量
     * 1. 如果目标值较小，则返回较少的即可。
     * @param targetSize 目标大小
     * @return 线程数量
     */
    public static int bestThreadNum(final int targetSize) {
        int bestNum = bestThreadNum();
        if(targetSize < bestNum) {
            return targetSize;
        }
        return bestNum;
    }

}
