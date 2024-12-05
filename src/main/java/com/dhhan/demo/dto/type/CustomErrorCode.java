package com.dhhan.demo.dto.type;

public enum CustomErrorCode {
    CUSTOM_ERROR_CODE_1("Custom Error Code 1"),
    HTTP_REQUEST_ERROR("HTTP 요청 에러") ;

    private String errorMessage;
    CustomErrorCode(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String s) {this.errorMessage = s; }
}
