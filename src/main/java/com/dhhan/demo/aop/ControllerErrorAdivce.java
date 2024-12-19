package com.dhhan.demo.aop;

import com.dhhan.customFramework.utils.LogHelper;
import com.dhhan.demo.dto.response.CustomResponse;
import com.dhhan.demo.dto.type.CustomErrorCode;
import com.dhhan.demo.dto.type.CustomStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerErrorAdivce {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse handleException(Exception e) {
        LogHelper.error(e);
        return CustomResponse.fail(CustomErrorCode.CUSTOM_ERROR_CODE_1);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponse handleException2(Exception e) {
        LogHelper.error(e);
        return CustomResponse.fail(CustomErrorCode.HTTP_REQUEST_ERROR);
    }

    @ExceptionHandler(StatusOkException.class)
    @ResponseStatus(HttpStatus.OK)
    public CustomResponse handleException3(StatusOkException e) {
        LogHelper.info("Status : "+e.getStatus()+
                ", ErrorCode : "+e.getErrorCode().toString() +
                ", ErrorMessage : "+e.getErrorCode().getErrorMessage()
                ,this);
        return new CustomResponse(e.getStatus(),e.getErrorCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CustomResponse handleException4(ForbiddenException e) {
        LogHelper.error(e);
        return CustomResponse.fail(CustomErrorCode.HTTP_REQUEST_ERROR);
    }
}
