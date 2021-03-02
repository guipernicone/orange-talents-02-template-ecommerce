package com.zup.mercadolivre.entity.product;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.exceptions.UnauthorizedRequest;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @NotNull
    @ManyToOne
    private User owner;

    @OneToMany(cascade=CascadeType.MERGE, mappedBy = "product")
    private List<ProductImages> images;

    @OneToMany(cascade=CascadeType.MERGE, mappedBy = "product")
    private List<Opinion> opinions;

    @OneToMany(cascade=CascadeType.MERGE, mappedBy = "product")
    private List<Question> questions;

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
            Category category,
            User user
    ) {
        Assert.hasLength(name, "The product name must not be empty");
        Assert.hasLength(description, "The product description must not be empty");
        Assert.notNull(price, "The product must have a price");
        Assert.notNull(category, "The product must have a category");
        Assert.isTrue(inventory >= 0, "The product inventory must be at least 0");
        Assert.isTrue(price.floatValue() > 0, "The product price must be more than 0");
        Assert.notNull(user, "The product must have a user");

        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.productCharacteristics = productCharacteristics;
        this.description = description;
        this.category = category;
        this.owner = user;
        this.registerTime = LocalDateTime.now();
        this.images = new ArrayList<>();
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

    public User getOwner() {
        return owner;
    }

    public List<ProductImages> getImages() {
        return images;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addImage(List<ProductImages> imagesList, String requestedLogin) throws UnauthorizedRequest {
        if (!this.owner.getLogin().equals(requestedLogin)){
            throw new UnauthorizedRequest("User not authorized to this operation");
        }

        this.images.addAll(imagesList);
    }

    public boolean updateInventory(int quantity) {
        if (quantity > 0 && quantity >= this.inventory) {
            this.inventory -= quantity;
            return true;
        }
        return false;
    }
}
