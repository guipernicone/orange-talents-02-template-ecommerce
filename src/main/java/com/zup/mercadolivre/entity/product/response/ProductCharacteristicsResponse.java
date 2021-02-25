package com.zup.mercadolivre.entity.product.response;

import com.zup.mercadolivre.entity.category.response.CategoryResponse;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCharacteristicsResponse {

    private String name;
    private String value;

    public ProductCharacteristicsResponse(ProductCharacteristics productCharacteristics){
        this.name = productCharacteristics.getName();
        this.value = productCharacteristics.getValue();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
