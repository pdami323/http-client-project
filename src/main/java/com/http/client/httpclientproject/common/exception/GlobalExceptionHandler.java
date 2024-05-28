package com.http.client.httpclientproject.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity handleException(UserException ex){
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(new ErrorResponse(ex.getErrorCode().getMessage(), ex.getErrorCode().getStatus()));
    }

}
