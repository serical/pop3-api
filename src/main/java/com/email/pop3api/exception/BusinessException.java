package com.email.pop3api.exception;


import com.email.pop3api.enums.ResultEnums;
import lombok.Getter;

/**
 * 自定义异常
 *
 * @author serical 2019-03-23 09:59:54
 */
@Getter
public class BusinessException extends RuntimeException {

    private int status;

    /**
     * 默认构造方法
     */
    public BusinessException() {
        super(ResultEnums.ERROR.getValue());
        this.status = ResultEnums.ERROR.getKey();
    }

    /**
     * 自定义错误消息枚举构造函数
     *
     * @param status 错误消息枚举
     */
    public BusinessException(ResultEnums status) {
        super(status.getValue());
        this.status = status.getKey();
    }

    /**
     * 自定义错误消息，默认状态为error
     *
     * @param msg 错误消息
     */
    public BusinessException(String msg) {
        super(msg);
        this.status = ResultEnums.ERROR.getKey();
    }
}
