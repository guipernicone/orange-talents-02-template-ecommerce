package com.zup.mercadolivre.controller.exceptionhandler.MethodArgumentNotValid;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

public class MethodArgumentNotValidError {


    int numberOfErrors;
    List<MethodArgumentNotValidErrorResponse> errorList = new ArrayList<>();

    /**
     * Build the error response
     *
     * @param exception An MethodArgumentNotValidException instance
     */
    public void buildError(MethodArgumentNotValidException exception){
        this.buildGlobalErrors(exception.getGlobalErrors());
        this.buildFieldErros(exception.getFieldErrors());

        numberOfErrors = this.errorList.size();
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public List<MethodArgumentNotValidErrorResponse> getErrorList() {
        return errorList;
    }

    private void buildGlobalErrors(List<ObjectError> globalErrors){
        globalErrors.forEach(error -> {
            MethodArgumentNotValidGlobalErrorResponse validationFieldError = new MethodArgumentNotValidGlobalErrorResponse(error.getObjectName(), error.getDefaultMessage());
            this.errorList.add(validationFieldError);
        }) ;
    }

    private void buildFieldErros(List<FieldError> fieldErros){
        fieldErros.forEach(error -> {
            MethodArgumentNotValidFieldErrorResponse validationFieldError = new MethodArgumentNotValidFieldErrorResponse(error.getField(), error.getDefaultMessage());
            this.errorList.add(validationFieldError);
        }) ;
    }
}
