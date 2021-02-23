package com.zup.mercadolivre.controller.exceptionhandler.MethodArgumentNotValid;

public class MethodArgumentNotValidFieldErrorResponse extends MethodArgumentNotValidErrorResponse {
    private String field;

    public MethodArgumentNotValidFieldErrorResponse(String field, String defaultMessage) {
        super(defaultMessage);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}