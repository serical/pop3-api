package com.email.pop3api.result;

import com.email.pop3api.enums.ResultEnums;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * API返回响应
 *
 * @author serical 2019-03-23 10:08:30
 */
@Component
public class Result {

    private static Hashids hashids;

    @Autowired
    public void setHashids(Hashids hashids) {
        Result.hashids = hashids;
    }

    /**
     * 成功不返回数据
     *
     * @return 响应体
     */
    public static ResultEntity success() {
        return result(ResultEnums.SUCCESS, null);
    }

    /**
     * 成功返回数据
     *
     * @param object 返回数据
     * @return 响应体
     */
    public static ResultEntity success(Object object) {
        return result(ResultEnums.SUCCESS, object);
    }

    /**
     * 失败响应体
     *
     * @return 错误响应体
     */
    public static ResultEntity error() {
        return result(ResultEnums.ERROR, null);
    }

    /**
     * 枚举构造
     *
     * @param status 状态枚举
     * @param data   返回数据
     * @return 响应体
     */
    private static ResultEntity result(ResultEnums status, Object data) {
        return result(status.getKey(), status.getValue(), data);
    }

    /**
     * 响应体构造
     *
     * @param status  状态码
     * @param message 消息
     * @param data    返回数据
     * @return 响应体
     */
    private static ResultEntity result(int status, String message, Object data) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setMessageId(hashids.encode(System.currentTimeMillis()));
        resultEntity.setStatus(status);
        resultEntity.setMessage(message);
        resultEntity.setData(data);
        return resultEntity;
    }
}

