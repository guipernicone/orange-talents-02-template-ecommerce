package com.zup.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidGatewayTransactionValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGatewayTransaction {
    String message() default "{Invalid Gateway Transaction}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
