package com.zup.mercadolivre.entity.user.response;

public class UserTokenResponse {
    private String token;
    private String type;
    public UserTokenResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
