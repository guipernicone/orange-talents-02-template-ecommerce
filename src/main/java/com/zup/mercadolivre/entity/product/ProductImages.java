package com.zup.mercadolivre.entity.product;

import javax.validation.constraints.NotBlank;

public class ProductImages {

    @NotBlank
    private String link;

    @Deprecated
    public ProductImages() {};

    public ProductImages(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
