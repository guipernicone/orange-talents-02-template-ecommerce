package com.zup.mercadolivre.controller.exceptionhandler;

import com.zup.mercadolivre.exceptions.UnauthorizedRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedRequest.class)
    public String UnauthorizedRequestHandler(UnauthorizedRequest unauthorizedRequest){
        return unauthorizedRequest.getMessage();
    }
}
