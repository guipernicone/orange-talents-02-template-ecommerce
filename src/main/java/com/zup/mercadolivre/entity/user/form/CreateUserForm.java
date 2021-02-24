package com.zup.mercadolivre.entity.user.form;

import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUserForm {

    @NotBlank(message="{NotBlank}")
    @Email(message="{Email}")
    @UniqueValue(uniqueField = "login", targetClass = User.class, message = "{UniqueValue}")
    private String login;

    @NotBlank(message="{NotBlank}")
    @Size(min=6, message ="{Size.password}")
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public User toModel(){
        return new User(this.login, this.password);
    }
}
