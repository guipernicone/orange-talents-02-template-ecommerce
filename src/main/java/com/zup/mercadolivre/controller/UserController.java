package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.entity.user.form.CreateUserForm;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<UserCreateResponse> createUser (@RequestBody @Valid CreateUserForm userForm)
    {
        User user = userForm.toModel();
        entityManager.persist(user);

        return ResponseEntity.ok(new UserCreateResponse(user));
    }
}
