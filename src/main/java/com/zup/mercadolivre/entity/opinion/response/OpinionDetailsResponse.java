package com.zup.mercadolivre.entity.opinion.response;

import com.zup.mercadolivre.entity.opinion.Opinion;
import io.jsonwebtoken.lang.Assert;

public class OpinionDetailsResponse {

    private String title;
    private String rating;
    private String description;
    private String user;

    public OpinionDetailsResponse(Opinion opinion) {
        Assert.notNull(opinion, "The opinion cannot be null");
        Assert.notNull(opinion.getUser(), "The user of an opinion cannot be null");

        this.title = opinion.getTitle();
        this.rating = opinion.getRating();
        this.description = opinion.getDescription();
        this.user = opinion.getUser().getLogin();
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

    public String getUser() {
        return user;
    }
}
