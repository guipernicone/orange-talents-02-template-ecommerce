package com.zup.mercadolivre.entity.product;

import com.zup.mercadolivre.entity.category.Category;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<ProductCharacteristics> productCharacteristics;

    @Size(max=1000)
    @Lob
    private String description;

    @NotNull
    @ManyToOne
    private Category category;

    private LocalDateTime registerTime;

    @Deprecated
    public Product() {
    }

    public Product(
            String name,
            BigDecimal price,
            int inventory,
            List<ProductCharacteristics> productCharacteristics,
            String description,
            Category category
    ) {
        Assert.hasLength(name, "The product name must not be empty");
        Assert.hasLength(description, "The product description must not be empty");
        Assert.notNull(price, "The product must have a price");
        Assert.notNull(category, "The product must have a category");
        Assert.isTrue(inventory >= 0, "The product inventory must be at least 0");
        Assert.isTrue(price.floatValue() > 0, "The product price must be more than 0");

        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.productCharacteristics = productCharacteristics;
        this.description = description;
        this.category = category;
        this.registerTime = LocalDateTime.now();
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

    public List<ProductCharacteristics> getProductCharacteristics() {
        return productCharacteristics;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }
}
