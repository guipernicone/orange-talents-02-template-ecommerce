package com.zup.mercadolivre.validation;

import com.zup.mercadolivre.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

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
