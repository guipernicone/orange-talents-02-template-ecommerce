package com.zup.mercadolivre.entity.user.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class UserLoginForm {

    @NotBlank(message = "{NotBlank}")
    private String login;

    @NotBlank(message = "{NotBlank}")
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        // TODO Auto-generated method stub
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }
}
