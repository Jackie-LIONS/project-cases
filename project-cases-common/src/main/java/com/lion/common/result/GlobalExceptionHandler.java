package com.lion.common.result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 统一异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理业务异常
    @ExceptionHandler(BusException.class)
    public BaseResult defaultExceptionHandler(HttpServletRequest req, HttpServletResponse resp, BusException e) {
        BaseResult baseResult = new BaseResult(e.getCode(),e.getMessage(),null);
        return baseResult;
    }


    // 处理系统异常
    @ExceptionHandler(Exception.class)
    public BaseResult defaultExceptionHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        e.printStackTrace();
        BaseResult baseResult = new BaseResult(CodeEnum.SYSTEM_ERROR.getCode(),CodeEnum.SYSTEM_ERROR.getMessage(),null);
        return baseResult;
    }
}
