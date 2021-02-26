package com.zup.mercadolivre.entity.product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="product_images")
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String link;

    @ManyToOne
    private Product product;

    @Deprecated
    public ProductImages() {};

    public ProductImages(String link, Product product) {
        this.link = link;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public Product getProduct() {
        return product;
    }
}
