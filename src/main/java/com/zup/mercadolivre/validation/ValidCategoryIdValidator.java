package com.zup.mercadolivre.validation;

import com.zup.mercadolivre.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Object> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null){
            return true;
        }

        return categoryRepository.existsById(Long.parseLong(String.valueOf(value)));
    }
}
