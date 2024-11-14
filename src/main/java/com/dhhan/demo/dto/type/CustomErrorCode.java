package com.dhhan.demo.dto.type;

public enum CustomErrorCode {
    CUSTOM_ERROR_CODE_1("Custom Error Code 1"),

    CUSTOM_ERROR_CODE_2("Custom Error Code 2") ;


    private String errorMessage;
    CustomErrorCode(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
