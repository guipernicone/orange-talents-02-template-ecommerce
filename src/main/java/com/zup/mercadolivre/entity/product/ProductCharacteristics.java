package com.zup.mercadolivre.entity.product;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="product_characteristics")
public class ProductCharacteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;
    @NotBlank
    private String value;

    @Deprecated
    public ProductCharacteristics() {
    }

    public ProductCharacteristics(String name, String value) {
        Assert.hasLength(name, "The Characteristics name cannot be blank");
        Assert.hasLength(value, "The Characteristics value cannot be blank");

        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
