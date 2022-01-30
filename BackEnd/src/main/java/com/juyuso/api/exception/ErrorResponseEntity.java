package com.juyuso.api.exception;

import lombok.Data;

@Data
public class ErrorResponseEntity {
    private int status;
    private String code;
    private String message;
    private String detail;

    public ErrorResponseEntity(ErrorCode errorCode , String detail) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.detail = detail;
    }
}
