package com.zup.mercadolivre.entity.question;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "{NotBlank}")
    private String title;
    @NotBlank(message = "{NotBlank}")
    private String question;
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private User user;
    @NotNull(message = "{NotNull}")
    @ManyToOne
    private Product product;
    @NotNull(message = "{NotNull}")
    private LocalDateTime publishDate;

    public Question(String title, String question, User user, Product product) {
        Assert.hasLength(title, "The title must not be empty");
        Assert.hasLength(question, "The question must not be empty");
        Assert.notNull(user, "Invalid User");
        Assert.notNull(product, "Invalid Product");

        this.title = title;
        this.question = question;
        this.user = user;
        this.product = product;
        this.publishDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }
}
