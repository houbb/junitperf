package com.github.houbb.junitperf.util;

import com.github.houbb.heaven.util.util.DateUtil;

/**
 * 日志工具类
 * <p> project: junitperf-ConsoleUtil </p>
 * <p> create on 2020/6/15 19:52 </p>
 *
 * @author binbin.hou
 * @since 2.0.4
 */
public final class ConsoleUtil {

    private ConsoleUtil(){}

    /**
     * 输出文档
     * @param format 文本格式化
     * @param args 参数
     * @since 2.0.4
     */
    public static void info(final String format, final Object ... args) {
        String dateStr = DateUtil.getCurrentDateTimeStr();
        String formatStr = buildString(format, args);
        System.out.println("[INFO] "+dateStr+" - " + formatStr);
    }

    /**
     * 格式化信息
     * @param format 格式化
     * @param params 参数
     * @return 结果
     * @since 2.0.4
     */
    private static String buildString(String format, Object[] params) {
        String stringFormat = format;

        for(int i = 0; i < params.length; ++i) {
            stringFormat = stringFormat.replaceFirst("\\{}", "%s");
        }

        return String.format(stringFormat, params);
    }

}
