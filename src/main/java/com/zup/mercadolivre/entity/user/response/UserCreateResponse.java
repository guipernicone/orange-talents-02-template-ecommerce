package com.zup.mercadolivre.entity.user.response;

import com.zup.mercadolivre.entity.user.User;

public class UserCreateResponse {
    private long id;
    private String login;

    public UserCreateResponse(User user){
        this.id = user.getId();
        this.login = user.getLogin();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
