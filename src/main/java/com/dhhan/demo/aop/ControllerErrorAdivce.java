package com.dhhan.demo.aop;

import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class ControllerErrorAdivce {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleException(Exception e) {
        Logger.getLogger("AA").log(Level.INFO,e.getMessage());
        return CustomResponse.fail(CustomErrorCode.CUSTOM_ERROR_CODE_1);
    }
}
