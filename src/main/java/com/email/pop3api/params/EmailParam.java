package com.email.pop3api.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 邮箱接口参数
 *
 * @author serical 2022-01-18 19:12:26
 */
@Data
public class EmailParam implements Serializable {

    /**
     * 用户名
     */
    private String userEmail;

    /**
     * pop3登录授权码
     */
    private String authorizeCode;
}
