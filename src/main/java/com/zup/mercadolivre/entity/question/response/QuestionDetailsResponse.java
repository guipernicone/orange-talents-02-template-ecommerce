package com.zup.mercadolivre.entity.question.response;

import com.zup.mercadolivre.entity.question.Question;
import io.jsonwebtoken.lang.Assert;

import java.time.LocalDateTime;

public class QuestionDetailsResponse {

    private String title;
    private String question;
    private LocalDateTime publishDate;
    private String user;

    public QuestionDetailsResponse(Question question) {
        Assert.notNull(question, "The question cannot be null");
        Assert.notNull(question.getUser(), "The User of a question cannot be null");

        this.title = question.getTitle();
        this.question = question.getQuestion();
        this.publishDate = question.getPublishDate();
        this.user = question.getUser().getLogin();
    }

    public String getTitle() {
        return title;
    }

    public String getQuestion() {
        return question;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public String getUser() {
        return user;
    }
}
