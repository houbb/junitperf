package com.github.houbb.junitperf.util;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.DateUtil;

import java.util.List;

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
     * 单行日志信息
     * @since 2.0.6
     */
    public static final String LINE = "--------------------------------------------------------";

    /**
     * 输出文档
     * @param format 文本格式化
     * @param args 参数
     * @since 2.0.4
     */
    public static void info(final String className,
                            final String methodName,
                            final String format,
                            final Object ... args) {
        String formatStr = buildString(format, args);
        log("INFO", className, methodName, formatStr, null);
    }

    /**
     * 输出文档
     * @param format 文本格式化
     * @param args 参数
     * @since 2.0.6
     */
    public static void info(final String format,
                            final Object ... args) {
        StackTraceElement callMethodElem = Thread.currentThread().getStackTrace()[3];
        String className = callMethodElem.getClassName();
        final String methodNameName = callMethodElem.getMethodName();

        info(className, methodNameName, format, args);
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

    /**
     * 消息打印
     *
     * final String threadName = Thread.currentThread().getName();
     * @param level 消息等级
     * @param content 内容
     * @param throwable 异常
     * @since 2.0.6
     */
    private static void log(final String level,
                     final String className,
                     final String methodName,
                     String content,
                     Throwable throwable) {
        final String prettyMethod = buildPrettyMethodName(className, methodName);

        String dateStr = DateUtil.getCurrentDateTimeStr();
        String log = String.format("[%s] [%s] [%s] - %s", level, dateStr, prettyMethod, content);
        if("ERROR".equalsIgnoreCase(level)) {
            System.err.println(log);
        } else {
            System.out.println(log);
        }

        if (throwable != null) {
            throwable.printStackTrace(System.err);
        }
    }

    /**
     * 构建更加优雅的方法名称
     * （1）className 只取首字母
     * @param className 类名
     * @param methodName 方法名称
     * @return 结果
     * @since 2.0.6
     */
    private static String buildPrettyMethodName(final String className, final String methodName) {
        String[] classNames = className.split("\\.");
        if(ArrayUtil.isEmpty(classNames)) {
            return methodName;
        }

        final int length = classNames.length;
        if(length == 1) {
            return className+ PunctuationConst.DOT+methodName;
        }

        // 类名超过一个
        List<String> classFirstChars = Guavas.newArrayList(length);
        for(int i = 0; i < length-1; i++) {
            String name = classNames[i];
            classFirstChars.add(String.valueOf(name.charAt(0)));
        }
        classFirstChars.add(classNames[length-1]);
        String prettyClass = CollectionUtil.join(classFirstChars, PunctuationConst.DOT);
        return prettyClass+PunctuationConst.DOT+methodName;
    }

}
