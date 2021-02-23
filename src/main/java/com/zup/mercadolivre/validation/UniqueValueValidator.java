package com.zup.mercadolivre.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String uniqueField;
    private Class<?> targetClass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue params) {
        uniqueField = params.uniqueField();
        targetClass = params.targetClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Query query = entityManager.createQuery("select 1 from " + targetClass.getName() +"  where " + uniqueField + "=:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();

        return list.isEmpty();
    }
}
