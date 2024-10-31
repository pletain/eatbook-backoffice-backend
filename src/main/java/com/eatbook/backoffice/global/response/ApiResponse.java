package com.eatbook.backoffice.global.response;

public record ApiResponse<T>(
        StatusCode code,
        String message,
        T data
) {
    public static <T> ApiResponse<T> of(StatusCode code, T data) {
        return new ApiResponse<>(code, code.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(StatusCode code) {
        return new ApiResponse<>(code, code.getMessage(), null);
    }
}

