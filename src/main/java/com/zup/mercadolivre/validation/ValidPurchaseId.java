package com.zup.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidPurchaseIdValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPurchaseId {
    String message() default "{Invalid Purchase Id}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
