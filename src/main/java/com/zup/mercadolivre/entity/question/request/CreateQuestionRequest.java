package com.zup.mercadolivre.entity.question.request;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.validation.ValidId;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateQuestionRequest {

    @NotBlank(message = "{NotBlank}")
    private String title;
    @NotBlank(message = "{NotBlank}")
    private String question;
    @NotNull(message = "{NotNull}")
    @ValidId(targetClass = Product.class)
    private long productId;

    @Deprecated
    public CreateQuestionRequest() {}

    public CreateQuestionRequest(String title, String question, long productId) {
        this.title = title;
        this.question = question;
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public long getProductId() {
        return productId;
    }

    public Question toModel(EntityManager entityManager, User user) {
        Product product = entityManager.find(Product.class, this.productId);

        Assert.notNull(product, "Invalid product id");
        Assert.notNull(user, "Invalid user");

        return new Question(
          this.title,
          this.question,
          user,
          product
        );
    }
}
