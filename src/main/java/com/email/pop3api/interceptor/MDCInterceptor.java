package com.email.pop3api.interceptor;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.hashids.Hashids;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成全局请求ID
 *
 * @author serical 2022-01-20 20:19:53
 */
@Component
@RequiredArgsConstructor
public class MDCInterceptor implements HandlerInterceptor {

    private final Hashids hashids;
    public final static String REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestId = hashids.encodeHex(IdUtil.fastSimpleUUID());
        MDC.put(REQUEST_ID, requestId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.remove(REQUEST_ID);
    }
}
