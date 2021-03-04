package com.zup.mercadolivre.entity.purcharse.request;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.validation.ValidId;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;

public class PurchaseRequest {
    @ValidId(targetClass = Product.class)
    private long productId;
    @Min(value = 1, message = "{Min.quantity}")
    private int quantity;
    @NotNull(message = "{NotNull}")
    private GatewayEnum gateway;

    @Deprecated
    public PurchaseRequest(){}

    public PurchaseRequest(long productId, int quantity, GatewayEnum gateway) {
        this.productId = productId;
        this.quantity = quantity;
        this.gateway = gateway;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public GatewayEnum getGateway() {
        return gateway;
    }

    public Purchase toModel(EntityManager entityManager, User user){
        Product product = entityManager.find(Product.class, this.productId);

        Assert.notNull(product, "The product must not be null");
        Assert.notNull(user, "The user must not be null");
        PurchaseStatusEnum status = PurchaseStatusEnum.NOT_INITIATE;

        if(product.updateInventory(this.quantity)){
            status = PurchaseStatusEnum.INITIATE;
            entityManager.persist(product);
        }

        return new Purchase(
                this.quantity,
                this.gateway,
                status,
                user,
                product
        );
    }
}
