package com.email.pop3api.enums;

import lombok.Getter;

/**
 * 响应体枚举
 *
 * @author serical 2019-03-23 10:09:10
 */
@Getter
public enum ResultEnums {

    /**
     * 系统相关
     */
    SUCCESS(0, ""),
    ERROR(-1, "系统错误"),
    PARAM_ERROR(1, "参数错误"),
    RATE_LIMIT(2, "超出请求频率"),

    ;


    private int key;
    private String value;

    ResultEnums(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
