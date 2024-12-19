package com.dhhan.demo.aop;

import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;

public class ForbiddenException extends RuntimeException {
    private CustomStatus status;
    private CustomErrorCode errorCode;
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(CustomStatus status, CustomErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.status = status;
        this.errorCode = errorCode;
    }
    public ForbiddenException(CustomStatus status, String message, CustomErrorCode errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

    public ForbiddenException(CustomStatus status, String message, CustomErrorCode errorCode, Throwable cause) {
        super(message,cause);
        this.status = status;
        this.errorCode = errorCode;
    }

    public ForbiddenException() {
        super();
    }

    public CustomErrorCode getErrorCode() {
        return errorCode;
    }

    public CustomStatus getStatus() {
        return status;
    }
}
