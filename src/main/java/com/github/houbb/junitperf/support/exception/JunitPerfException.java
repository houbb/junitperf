package com.github.houbb.junitperf.support.exception;

/**
 * 性能测试异常
 * 要求：本项目中所有异常统一使用本类进行处理
 * @author bbhou
 * @version 1.0.2
 * @since 1.0.2, 2018/01/29
 */
public class JunitPerfException extends Exception {

    public JunitPerfException() {
    }

    public JunitPerfException(String message) {
        super(message);
    }

    public JunitPerfException(String message, Throwable cause) {
        super(message, cause);
    }

    public JunitPerfException(Throwable cause) {
        super(cause);
    }

    public JunitPerfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
