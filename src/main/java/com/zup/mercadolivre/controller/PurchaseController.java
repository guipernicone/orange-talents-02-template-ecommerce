package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.NotaFiscal.NotaFiscalRequest;
import com.zup.mercadolivre.entity.email.EmailPurchase;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseGatewayRequest;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseRequest;
import com.zup.mercadolivre.entity.sellerRanking.SellerRankingRequest;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        if (purchase.getStatus() != PurchaseStatusEnum.INITIATE.getValue()){
            return ResponseEntity.badRequest().body("Product out of stock");
        }
        entityManager.persist(purchase);
        EmailPurchase email = new EmailPurchase(purchase);
        email.sendPurchaseEmail();
        return ResponseEntity.status(HttpStatus.FOUND).body(purchase.generateURL());
    }

    @PostMapping("/gateway")
    @Transactional
    public ResponseEntity<?> gatewayReturn(@RequestBody @Valid PurchaseGatewayRequest gatewayRequest){
        GatewayPayment gatewayPayment = gatewayRequest.toModel(entityManager);
        entityManager.persist(gatewayPayment);

        Purchase purchase = gatewayPayment.getPurchase();
        EmailPurchase email = new EmailPurchase(purchase);

        if (gatewayPayment.getStatus() == GatewayStatusEnum.ERRO.getValue()) {
            email.sendErrorFeedbackEmail();
            return ResponseEntity.ok().body(gatewayPayment.getPurchase().getStatus());
        }

        email.sendSuccessFeedbackEmail();

        purchase.updateStatus(PurchaseStatusEnum.FINISHED);

        NotaFiscalRequest notaFiscalRequest = new NotaFiscalRequest(
                String.valueOf(gatewayPayment.getPurchase().getUser().getId()),
                String.valueOf(gatewayPayment.getPurchase().getId())
        );

        SellerRankingRequest sellerRequest = new SellerRankingRequest(
                String.valueOf(gatewayPayment.getPurchase().getId()),
                String.valueOf(gatewayPayment.getPurchase().getProduct().getOwner().getId())
        );

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<NotaFiscalRequest> notaFiscalRequestHttpEntity = new HttpEntity<>(notaFiscalRequest);
        String notaFiscalUrl = "http://localhost:8080/nota-fiscal";
        restTemplate.exchange(notaFiscalUrl, HttpMethod.POST, notaFiscalRequestHttpEntity, NotaFiscalRequest.class);


        HttpEntity<SellerRankingRequest> sellerRankingRequest = new HttpEntity<>(sellerRequest);
        String sellerRankingUrl = "http://localhost:8080/seller";
        restTemplate.exchange(sellerRankingUrl, HttpMethod.POST, sellerRankingRequest, SellerRankingRequest.class);

        return ResponseEntity.ok().body(gatewayPayment.getPurchase().getStatus());
    }
}
