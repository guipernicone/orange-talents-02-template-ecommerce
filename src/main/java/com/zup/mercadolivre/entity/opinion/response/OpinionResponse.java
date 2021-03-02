package com.zup.mercadolivre.entity.opinion.response;

import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.product.response.ProductResponse;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OpinionResponse
{
    private long id;
    private String title;
    private String rating;
    private String description;
    private UserCreateResponse user;
    private ProductResponse product;

    public OpinionResponse(Opinion opinion) {
        this.id = opinion.getId();
        this.title = opinion.getTitle();
        this.rating = opinion.getRating();
        this.description = opinion.getDescription();
        this.user = new UserCreateResponse(opinion.getUser());
        this.product = new ProductResponse(opinion.getProduct());
    }

    public long getId() {
        return id;
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

    public UserCreateResponse getUser() {
        return user;
    }

    public ProductResponse getProduct() {
        return product;
    }
}
