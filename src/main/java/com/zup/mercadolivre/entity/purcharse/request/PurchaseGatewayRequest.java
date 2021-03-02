package com.zup.mercadolivre.entity.purcharse.request;

import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;

public class PurchaseGatewayRequest {

    private long gatewayId;
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

    @Override
    public String toString() {
        return "PurchaseGatewayRequest{" +
                "gatewayId=" + gatewayId +
                ", purchaseId=" + purchaseId +
                ", status=" + status +
                '}';
    }
}
