package com.zup.mercadolivre.entity.user;

import com.zup.mercadolivre.validation.UniqueValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name="user")
public class User implements UserDetails {

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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
