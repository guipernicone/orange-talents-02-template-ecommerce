package com.zup.mercadolivre.controller.exceptionhandler;


import com.zup.mercadolivre.controller.exceptionhandler.MethodArgumentNotValid.MethodArgumentNotValidError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MethodArgumentNotValidError handlerValidationError(MethodArgumentNotValidException exception){
        MethodArgumentNotValidError validationError = new MethodArgumentNotValidError();
        validationError.buildError(exception);
        return validationError;
    }
}
