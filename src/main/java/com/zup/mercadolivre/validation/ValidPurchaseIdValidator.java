package com.zup.mercadolivre.validation;

import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPurchaseIdValidator implements ConstraintValidator<ValidPurchaseId,Object> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Purchase purchase = entityManager.find(Purchase.class, Long.parseLong(String.valueOf(value)));
        return purchase != null && purchase.getStatus() != PurchaseStatusEnum.FINISHED.getValue();
    }
}
