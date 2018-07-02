package com.github.houbb.junitperf.constant.enums;

import com.github.houbb.junitperf.constant.VersionConstant;

import org.apiguardian.api.API;

/**
 * 状态枚举
 * @author bbhou
 * @version 1.0.0
 * @since 1.0.0, 2018/01/15
 */
@API(status = API.Status.INTERNAL, since = VersionConstant.V2_0_0)
public enum  StatusEnum {

    /**
     * 通过
     */
    PASSED("PASSED"),
    /**
     * 失败
     */
    FAILED("FAILED")
    ;

    /**
     * 状态
     */
    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
