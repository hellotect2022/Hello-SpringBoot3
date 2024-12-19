package com.dhhan.demo.dto.type;

public enum CustomErrorCode {

    // 로그인 관련
    ERR_DUP_USERS("중복된 사용자 계정입니다."),
    ERR_NO_USERS("사용자 정보가 없습니다."),
    ERR_NO_MATCH_PASSWD("사용자 비밀번호가 맞지 않습니다."),
    HTTP_REQUEST_ERROR("HTTP 요청 에러"),
    CUSTOM_ERROR_CODE_1("Custom Error Code 1")
    ;

    private String errorMessage;
    CustomErrorCode(String s) {
        this.errorMessage = s;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String s) {this.errorMessage = s; }
}
