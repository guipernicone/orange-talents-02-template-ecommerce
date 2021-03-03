package com.zup.mercadolivre.repository;

import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GatewayPaymentRepository extends CrudRepository<GatewayPayment, Long> {

    Optional<GatewayPayment> findByGatewayId(long gatewayId);
}
