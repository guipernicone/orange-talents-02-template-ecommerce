package com.zup.mercadolivre.entity.purcharse;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.user.User;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name= "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Min(value = 1, message = "{Size.quantity}")
    private int quantity;
    private int gateway = GatewayEnum.PAYPAL.getValue();
    private int status = PurchaseStatusEnum.NOT_INITIATE.getValue();
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private User user;
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private Product product;

    public Purchase(int quantity, GatewayEnum gateway, PurchaseStatusEnum status, User user, Product product) {
        Assert.isTrue(quantity >= 1 , "The quantity must be higher than 1");
        Assert.notNull(gateway, "Invalid gateway");
        Assert.notNull(status, "Invalid status");
        Assert.notNull(user, "Invalid user");
        Assert.notNull(product, "Invalid product");

        this.quantity = quantity;
        this.gateway = gateway.getValue();
        this.status = status.getValue();
        this.user = user;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getGateway() {
        return gateway;
    }

    public int getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String generateURL() {
        if (gateway == GatewayEnum.PAYPAL.getValue()){
            return "paypal.com/"+ this.id +"?redirectUrl=urlRetornoAppPosPagamento";
        }
       else{
           return "pagseguro.com?returnId="+ this.id +"&redirectUrl=urlRetornoAppPosPagamento";
        }
    }
}
