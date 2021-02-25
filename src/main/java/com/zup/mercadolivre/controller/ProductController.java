package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.request.CreateProductRequest;
import com.zup.mercadolivre.entity.product.response.ProductResponse;
import com.zup.mercadolivre.repository.ProductRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest productRequest){
        Product product = productRequest.toModel(entityManager);
        entityManager.persist(product);

        return ResponseEntity.ok(new ProductResponse(product));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductResponse>> getProducts(){
        List<Product> productList = productRepository.findAll();
        List<ProductResponse> productResponseList = productList.stream().map(ProductResponse::new).collect(Collectors.toList());

        return ResponseEntity.ok(productResponseList);
    }
}
