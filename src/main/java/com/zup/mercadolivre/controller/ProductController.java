package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductImages;
import com.zup.mercadolivre.entity.product.request.CreateProductRequest;
import com.zup.mercadolivre.entity.product.request.ImageProductRequest;
import com.zup.mercadolivre.entity.product.response.ProductImageResponse;
import com.zup.mercadolivre.entity.product.response.ProductResponse;
import com.zup.mercadolivre.exceptions.UnauthorizedRequest;
import com.zup.mercadolivre.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest productRequest, Authentication authentication){
        Product product = productRequest.toModel(entityManager, authentication.getName());
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

    @PostMapping("/{product_id}/images")
    @Transactional
    public ResponseEntity<?> addProductsImages(
            @PathVariable("product_id") long productId,
            @RequestBody @Valid ImageProductRequest imageRequest,
            Authentication authentication
    ) throws UnauthorizedRequest {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid Product Id");
        }

        Product product = productOptional.get();
        List<ProductImages> productImages = imageRequest.toModel(product);

        product.addImage(productImages, authentication.getName());
        entityManager.persist(product);

        return ResponseEntity.ok(new ProductImageResponse(product));
    }
}
