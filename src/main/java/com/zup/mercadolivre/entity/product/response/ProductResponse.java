package com.zup.mercadolivre.entity.product.response;

import com.zup.mercadolivre.entity.category.response.CategoryResponse;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private int inventory;
    private List<ProductCharacteristicsResponse> productCharacteristics;
    private String description;
    private CategoryResponse category;
    private UserCreateResponse owner;
    private LocalDateTime registerTime;

    @Deprecated
    public ProductResponse(){}

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.inventory = product.getInventory();
        this.productCharacteristics = product.getProductCharacteristics()
                .stream()
                .map(ProductCharacteristicsResponse::new)
                .collect(Collectors.toList());
        this.description = product.getDescription();
        this.category = new CategoryResponse(product.getCategory());
        this.owner = new UserCreateResponse(product.getOwner());
        this.registerTime = product.getRegisterTime();

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public List<ProductCharacteristicsResponse> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public UserCreateResponse getOwner() {
        return owner;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }
}
