package com.email.pop3api.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.email.pop3api.params.EmailParam;
import com.email.pop3api.result.Result;
import com.email.pop3api.service.EmailService;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * pop3接收邮件接口
 *
 * @author serical 2022-01-18 07:40:39
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    /**
     * 心跳检测接口
     */
    @PostMapping("/ping")
    public Object ping() {
        return "pong";
    }

    /**
     * pop3协议通过邮箱和授权码登录获取最后一封邮件
     *
     * @param param 参数
     */
    @PostMapping("/getLastEmail")
    public Object getLastEmail(@RequestBody EmailParam param) {
        Preconditions.checkArgument(StrUtil.isNotBlank(param.getUserEmail()), "邮箱不能为空");
        Preconditions.checkArgument(Validator.isEmail(param.getUserEmail()), "邮箱格式错误");
        Preconditions.checkArgument(StrUtil.isNotBlank(param.getAuthorizeCode()), "pop登录授权码不能为空");

        return Result.success(emailService.getLastEmail(param));
    }
}
