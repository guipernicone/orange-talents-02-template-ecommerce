package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.NotaFiscal.NotaFiscalRequest;
import com.zup.mercadolivre.entity.sellerRanking.SellerRankingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/seller")
public class SellerRankingController {

    @PostMapping
    @Transactional
    public ResponseEntity<String> createSellerRanking(@RequestBody @Valid SellerRankingRequest sellerRankingRequest)
    {
        return ResponseEntity.ok().build();
    }
}
