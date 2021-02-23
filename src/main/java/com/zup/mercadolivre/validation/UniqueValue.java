package com.zup.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueValueValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {
    String message() default "{The field must be unique}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String uniqueField();
    Class<?> targetClass();
}