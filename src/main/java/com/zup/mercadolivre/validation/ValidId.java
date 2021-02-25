package com.zup.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidIdValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidId {
    String message() default "{Invalid id}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    Class<?> targetClass();
}
