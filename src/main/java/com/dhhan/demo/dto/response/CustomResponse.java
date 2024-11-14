package com.dhhan.demo.dto.response;

import com.dhhan.demo.dto.MemoryInfo;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;

public class CustomResponse<T> {

    private CustomStatus status;
    private T data;
    private CustomErrorCode errorCode;
    private String errorMessage;

    public CustomResponse(CustomStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    CustomResponse(CustomStatus status, CustomErrorCode errorCode) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getErrorMessage();
    }

    public static <T> CustomResponse<T> success(T data) {
        return new CustomResponse<>(CustomStatus.SUCCESS, data);
    }

    public static CustomResponse fail(CustomErrorCode errorCode) {
        return new CustomResponse<>(CustomStatus.FAIL, errorCode);
    }
    public CustomStatus getStatus() {
        return status;
    }

    public void setStatus(CustomStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CustomErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(CustomErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
