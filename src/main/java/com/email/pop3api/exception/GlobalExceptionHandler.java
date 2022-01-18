package com.email.pop3api.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.email.pop3api.enums.ResultEnums;
import com.email.pop3api.result.Result;
import com.email.pop3api.result.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * 全局异常处理器
 *
 * @author serical 2019-03-23 10:00:07
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * rest api common exception handler
     */
    @ExceptionHandler(Exception.class)
    public Object exception(Exception e) {
        ResultEntity error = Result.error();
        if (e instanceof BusinessException) {
            BusinessException exception = (BusinessException) e;
            error.setStatus(exception.getStatus());
            error.setMessage(exception.getMessage());
        }
        if (e instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) e;
            error.setStatus(ResultEnums.PARAM_ERROR.getKey());
            error.setMessage(exception.getMessage());
        }
        if (e instanceof BindException) {
            BindException exception = (BindException) e;
            error.setStatus(ResultEnums.PARAM_ERROR.getKey());
            error.setMessage(Optional.of(exception)
                    .map(BindException::getFieldError)
                    .map(v -> "[" + v.getField() + "]" + v.getDefaultMessage())
                    .orElse(""));
        }
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            error.setStatus(ResultEnums.PARAM_ERROR.getKey());
            error.setMessage(Optional.of(exception)
                    .map(MethodArgumentNotValidException::getBindingResult)
                    .map(Errors::getFieldError)
                    .map(v -> "[" + v.getField() + "]" + v.getDefaultMessage())
                    .orElse(""));
        }
        log.error("messageId:{}, error: {}", error.getMessageId(), ExceptionUtil.stacktraceToString(e));
        return error;
    }
}
