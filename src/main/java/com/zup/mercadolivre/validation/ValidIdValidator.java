package com.zup.mercadolivre.validation;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidIdValidator implements ConstraintValidator<ValidId, Object> {

    private Class<?> targetClass;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void initialize(ValidId params) {
        targetClass = params.targetClass();
    }
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        return  entityManager.find(targetClass, Long.parseLong(String.valueOf(value))) != null;
    }
}
