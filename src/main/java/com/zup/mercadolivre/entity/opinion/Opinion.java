package com.zup.mercadolivre.entity.opinion;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="opinion")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "{NotBlank}")
    private String title;
    @NotNull(message = "{NotNull}")
    @Pattern(regexp="(^[1-5])", message = "{Pattern.rating}")
    private String rating;
    @NotBlank(message = "{NotBlank}")
    @Size(max = 500, message = "Size.description")
    @Lob
    private String description;
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private User user;
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private Product product;

    @Deprecated
    public Opinion (){}

    public Opinion(String title, String rating, String description, User user, Product product) {
        Assert.hasLength(title, "The Opinion must have a title");
        Assert.isTrue(
                (Integer.parseInt(rating) >= 1 && Integer.parseInt(rating) <= 5),
                "The Rating must be between 1 and 5"
        );
        Assert.hasLength(description, "The Opinion must have a description");
        Assert.notNull(user, "Invalid user");
        Assert.notNull(product, "Invalid product");
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.user = user;
        this.product = product;
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

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
