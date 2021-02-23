package com.zup.mercadolivre.entity.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.login = login;
        this.password = encoder.encode(password);
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
