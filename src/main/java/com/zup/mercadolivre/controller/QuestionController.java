package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.Email.EmailManager;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.question.request.CreateQuestionRequest;
import com.zup.mercadolivre.entity.question.response.QuestionResponse;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.repository.UserRepository;
import com.zup.mercadolivre.validation.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<QuestionResponse> createQuestion(
            @RequestBody @Valid CreateQuestionRequest questionRequest,
            Authentication authentication
    ) {
        Optional<User> userOptional = userRepository.findByLogin(authentication.getName());

        if (userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Question question = questionRequest.toModel(entityManager, userOptional.get());
        entityManager.persist(question);

        EmailManager emailManager = new EmailManager(question);
        emailManager.sendEmail();
        return ResponseEntity.ok(new QuestionResponse(question));
    }
}
