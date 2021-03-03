package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.NotaFiscal.NotaFiscalRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/nota-fiscal")
public class NotaFiscalController {

    @PostMapping
    @Transactional
    public ResponseEntity<String> createNotaFiscal(@RequestBody @Valid NotaFiscalRequest notaFiscalRequest)
    {
        return ResponseEntity.ok().build();
    }
}
