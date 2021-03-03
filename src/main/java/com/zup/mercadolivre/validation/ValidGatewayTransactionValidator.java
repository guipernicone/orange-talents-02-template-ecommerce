package com.zup.mercadolivre.validation;

import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.repository.GatewayPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ValidGatewayTransactionValidator implements ConstraintValidator<ValidGatewayTransaction, Object> {
    @Autowired
    GatewayPaymentRepository gatewayPaymentRepository;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<GatewayPayment> gatewayPaymentOptional =
                gatewayPaymentRepository.findByGatewayId(Long.parseLong(value.toString()));

        if (gatewayPaymentOptional.isEmpty()){
            return true;
        }
        GatewayPayment gatewayPayment = gatewayPaymentOptional.get();

        return gatewayPayment.getStatus() != GatewayStatusEnum.SUCESSO.getValue();
    }
}
