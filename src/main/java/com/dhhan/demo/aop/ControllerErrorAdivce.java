package com.dhhan.demo.aop;

import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.utils.LogHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdivce {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleException(Exception e) {
        LogHelper.error(e);
        return CustomResponse.fail(CustomErrorCode.CUSTOM_ERROR_CODE_1);
    }
}
