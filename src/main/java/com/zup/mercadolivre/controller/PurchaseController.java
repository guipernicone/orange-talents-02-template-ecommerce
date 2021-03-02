package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.email.EmailPurchase;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseGatewayRequest;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseRequest;
import com.zup.mercadolivre.entity.purcharse.response.PurchaseResponse;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createPurchase(
            @RequestBody @Valid PurchaseRequest purchaseRequest,
            Authentication authentication
    ) {
        Optional<User> userOptional = userRepository.findByLogin(authentication.getName());

        if (userOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Purchase purchase = purchaseRequest.toModel(entityManager, userOptional.get());
        entityManager.persist(purchase);

        if (purchase.getStatus() != PurchaseStatusEnum.INITIATE.getValue()){
            return ResponseEntity.badRequest().body("Product out of stock");
        }
        EmailPurchase email = new EmailPurchase(purchase);
        email.sendEmail();
        return ResponseEntity.status(HttpStatus.FOUND).body(purchase.generateURL());
    }

    @PostMapping("/gateway")
    @Transactional
    public ResponseEntity<?> gatewayReturn(@RequestBody @Valid PurchaseGatewayRequest gatewayRequest){
        System.out.println(gatewayRequest.toString());
        return null;
    }
}
