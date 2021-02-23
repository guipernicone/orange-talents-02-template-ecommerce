package com.zup.mercadolivre.controller.exceptionhandler.MethodArgumentNotValid;

public class MethodArgumentNotValidErrorResponse {
    private String message;

    public MethodArgumentNotValidErrorResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}