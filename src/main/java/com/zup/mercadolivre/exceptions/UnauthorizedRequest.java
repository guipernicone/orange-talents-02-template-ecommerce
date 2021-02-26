package com.zup.mercadolivre.exceptions;

public class UnauthorizedRequest extends Exception{

    public UnauthorizedRequest (String message) {
        super(message);
    }
}
