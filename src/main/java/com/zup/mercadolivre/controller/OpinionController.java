package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.opinion.request.OpinionRequest;
import com.zup.mercadolivre.entity.opinion.response.OpinionResponse;
import com.zup.mercadolivre.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/opinion")
public class OpinionController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<OpinionResponse> createOpinion(
            @RequestBody @Valid OpinionRequest opinionRequest,
            Authentication authentication
    ) {
        Opinion opinion = opinionRequest.toModel(entityManager, (User) authentication.getPrincipal());
        entityManager.persist(opinion);
        return ResponseEntity.ok(new OpinionResponse(opinion));
    }
}