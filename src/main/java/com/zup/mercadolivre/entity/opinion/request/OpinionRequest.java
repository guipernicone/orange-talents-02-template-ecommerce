package com.zup.mercadolivre.entity.opinion.request;

import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Lob;
import javax.validation.constraints.*;

public class OpinionRequest {
    @NotBlank(message = "{NotBlank}")
    private String title;
    @NotNull(message = "{NotNull}")
    @Pattern(regexp="(^[1-5])", message = "{Pattern.rating}")
    private String rating;
    @NotBlank(message = "{NotBlank}")
    @Size(max = 500, message = "{Size.description}")
    private String description;
    @NotNull(message = "{NotNull}")
    private long productId;

    @Deprecated
    public OpinionRequest(){}

    public OpinionRequest(
            String title,
            String rating,
            String description,
            long productId
    ) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public long getProductId() {
        return productId;
    }

    public Opinion toModel(EntityManager entityManager, User user){
        Product product = entityManager.find(Product.class, this.productId);

        Assert.notNull(user, "Invalid user");
        Assert.notNull(product, "Invalid product");

        return new Opinion(
                this.title,
                this.rating,
                this.description,
                user,
                product
        );
    }
}
