package com.zup.mercadolivre.entity.purcharse;

import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="gateway_payment")
public class GatewayPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long gatewayId;
    @NotNull
    @ManyToOne
    private Purchase purchase;
    private int status;
    private LocalDateTime localDateTime;

    @Deprecated
    public GatewayPayment(){}

    public GatewayPayment(long gatewayId, Purchase purchase, GatewayStatusEnum status) {
        Assert.notNull(purchase, "Purchase cannot be null");
        Assert.notNull(status, "Gateway status must not be null");

        this.gatewayId = gatewayId;
        this.purchase = purchase;
        this.status = status.getValue();
        this.localDateTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public long getGatewayId() {
        return gatewayId;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void updateStatus(GatewayStatusEnum statusEnum){
        Assert.isTrue(
                this.status != GatewayStatusEnum.SUCESSO.getValue()
                , "The transaction is already finished and cannot be changed"
        );

        this.status = statusEnum.getValue();
    }
}
