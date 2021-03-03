package com.zup.mercadolivre.entity.purcharse.request;

import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.repository.GatewayPaymentRepository;
import com.zup.mercadolivre.validation.ValidGatewayTransaction;
import com.zup.mercadolivre.validation.ValidId;
import com.zup.mercadolivre.validation.ValidPurchaseId;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PurchaseGatewayRequest {

    @ValidGatewayTransaction(message = "{ValidGatewayTransaction}")
    private long gatewayId;
    @ValidPurchaseId
    private long purchaseId;

    private GatewayStatusEnum status;


    public long getGatewayId() {
        return gatewayId;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public GatewayStatusEnum getStatus() {
        return status;
    }

    public GatewayPayment toModel(EntityManager entityManager){

        Query query = entityManager.createQuery("select a from " + GatewayPayment.class.getName() +" a where gatewayId =:value");
        query.setParameter("value", this.gatewayId);
        Object queryResult = query.getResultList().stream().findFirst().orElse(null);

        if (queryResult == null) {
            Purchase purchase = entityManager.find(Purchase.class, this.purchaseId);
            return new GatewayPayment(
                    this.gatewayId,
                    purchase,
                    this.status
            );
        }

        GatewayPayment gatewayPayment = (GatewayPayment) queryResult;
        Assert.isTrue(
                gatewayPayment.getStatus() != GatewayStatusEnum.SUCESSO.getValue(),
                "Transaction already finished"
        );
        gatewayPayment.updateStatus(this.status);
        return gatewayPayment;
    }

}
