package com.zup.mercadolivre.entity.product.request;

import com.zup.mercadolivre.entity.product.ProductCharacteristics;

import javax.validation.constraints.NotBlank;

public class CreateProductCharacteristicsRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String value;

    @Deprecated
    public CreateProductCharacteristicsRequest() {}

    public CreateProductCharacteristicsRequest(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public ProductCharacteristics toModel(){
        return new ProductCharacteristics(name, value);
    }
}
