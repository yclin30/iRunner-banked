package com.yclin.irunnerbanked.exception;

import com.yclin.irunnerbanked.common.BaseResponse;
import com.yclin.irunnerbanked.common.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // 捕获所有运行时异常
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        // 实际项目中会记录日志
        return ResultUtils.error(500, "服务器错误: " + e.getMessage());
    }
}