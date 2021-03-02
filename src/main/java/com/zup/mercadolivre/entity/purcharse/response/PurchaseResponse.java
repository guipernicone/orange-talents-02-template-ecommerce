package com.zup.mercadolivre.entity.purcharse.response;

import com.zup.mercadolivre.entity.product.response.ProductResponse;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;
import org.springframework.security.core.userdetails.UserDetails;

public class PurchaseResponse
{
    private long id;
    private int quantity;
    private int status;
    private int gateway;
    private UserCreateResponse user;
    private ProductResponse product;

    public PurchaseResponse (Purchase purchase){
        this.id = purchase.getId();
        this.quantity = purchase.getQuantity();
        this.status = purchase.getStatus();
        this.gateway = purchase.getGateway();
        this.user = new UserCreateResponse(purchase.getUser());
        this.product = new ProductResponse(purchase.getProduct());
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStatus() {
        return status;
    }

    public int getGateway() {
        return gateway;
    }

    public UserCreateResponse getUser() {
        return user;
    }

    public ProductResponse getProduct() {
        return product;
    }
}
