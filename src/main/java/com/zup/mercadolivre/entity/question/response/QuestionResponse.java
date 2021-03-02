package com.zup.mercadolivre.entity.question.response;

import com.zup.mercadolivre.entity.product.response.ProductResponse;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;

import java.time.LocalDateTime;

public class QuestionResponse {

    private long id;
    private String title;
    private String question;
    private UserCreateResponse user;
    private ProductResponse product;
    private LocalDateTime publishDate;

    public QuestionResponse(Question question){
        this.id = question.getId();
        this.title = question.getTitle();
        this.question = question.getQuestion();
        this.user = new UserCreateResponse(question.getUser());
        this.product = new ProductResponse(question.getProduct());
        this.publishDate = question.getPublishDate();
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

    public UserCreateResponse getUser() {
        return user;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }
}
