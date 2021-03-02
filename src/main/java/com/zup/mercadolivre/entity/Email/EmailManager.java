package com.zup.mercadolivre.entity.Email;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.user.User;
import io.jsonwebtoken.lang.Assert;

public class EmailManager {

    private User fromUser;
    private User toUser;
    private Product product;
    private Question question;

    public EmailManager(Question question){
        Assert.notNull(question, "The question must not be null");
        Assert.notNull(question.getUser(), "The Question User cannot be null");
        Assert.notNull(question.getProduct(), "The Question product cannot be null");
        Assert.notNull(question.getProduct().getOwner(), "The User owner of the product must not be null");

        this.fromUser = question.getUser();
        this.toUser = question.getProduct().getOwner();
        this.product = question.getProduct();
        this.question = question;
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public Product getProduct() {
        return product;
    }

    public Question getQuestion() {
        return question;
    }

    public void sendEmail(){
        System.out.printf(
                "\n\nFrom: %s Id: %s\nTo: %s Id: %s\nProduct Id: %s Product Name: %s\nPublish Date: %s\nQuestion: %s\n\n",
                this.fromUser.getLogin(),
                String.valueOf(this.fromUser.getId()),
                this.toUser.getLogin(),
                String.valueOf(this.toUser.getId()),
                String.valueOf(this.product.getId()),
                this.product.getName(),
                this.question.getPublishDate().toString(),
                this.question.getQuestion()
        );
    }
}
