package com.email.pop3api.config;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数日志切面
 *
 * @author serical 2022-01-20 12:03:11
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class RequestAspect {


    /**
     * 请求参数拦截点
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void request() {
    }

    /**
     * 打印请求信息
     *
     * @param joinpoint 参数
     */
    @Before("request()")
    public void doBefore(JoinPoint joinpoint) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            log.info("rest ip={}, url={}, method={}, class_method={}, param={}", ServletUtil.getClientIP(request),
                    request.getRequestURI(), request.getMethod(),
                    joinpoint.getSignature(), joinpoint.getArgs());
        }
    }
}
