package com.email.pop3api.result;

import lombok.Data;

/**
 * 响应对象
 *
 * @author serical 2019-03-23 10:08:59
 */
@Data
public class ResultEntity {

    private String messageId;
    private int status;
    private String message;
    private Object data;

}
