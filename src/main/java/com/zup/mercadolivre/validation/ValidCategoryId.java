package com.zup.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidCategoryIdValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCategoryId {
    String message() default "{Invalid Category id}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
