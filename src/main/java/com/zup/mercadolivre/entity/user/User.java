package com.zup.mercadolivre.entity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message="{NotBlank}")
    @Email(message="{Email}")
    private String login;

    @NotBlank(message="{NotBlank}")
    @Size(min=6, message ="{Size.password}")
    private String password;

    private LocalDateTime registrationTime;

    @Deprecated
    public User() {}

    public User(String login, String password) {

        Assert.hasLength(login, "The login cannot be empty");
        Assert.hasLength(password, "The password cannot be empty");
        Assert.isTrue(password.length() >= 6, "The password must have more than 6 characters");

        this.login = login;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.registrationTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }
}
