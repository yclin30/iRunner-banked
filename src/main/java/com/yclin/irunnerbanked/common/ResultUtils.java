package com.yclin.irunnerbanked.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success");
    }

    public static BaseResponse error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }
}