package com.zup.mercadolivre.entity.product.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.validation.ValidId;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(value = 1)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    private int inventory;

    @NotNull
    @Size(min=3)
    private List<CreateProductCharacteristicsRequest> productCharacteristics;

    @Size(max=1000)
    private String description;

    @NotBlank
    @ValidId(targetClass = Category.class)
    private String categoryId;

    @Deprecated
    public CreateProductRequest() {
    }

    public CreateProductRequest(
            String name,
            BigDecimal price,
            int inventory,
            List<CreateProductCharacteristicsRequest> productCharacteristics,
            String description,
            String categoryId
    ) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.productCharacteristics = productCharacteristics;
        this.description = description;
        this.categoryId = categoryId;
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

    public List<CreateProductCharacteristicsRequest> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Product toModel(EntityManager entityManager){

        Category category = entityManager.find(Category.class, Long.parseLong(this.categoryId));

        Assert.notNull(category, "The category with id (" +this.categoryId+") was not found");

        List<ProductCharacteristics> productCharacteristicsList =
                productCharacteristics
                        .stream()
                        .map(CreateProductCharacteristicsRequest::toModel)
                        .collect(Collectors.toList());

        return new Product(
                this.name,
                this.price,
                this.inventory,
                productCharacteristicsList,
                this.description,
                category
        );
    }
}
