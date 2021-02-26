package com.zup.mercadolivre.controller;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.category.form.CreateCategoryForm;
import com.zup.mercadolivre.entity.category.response.CategoryResponse;
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
@RequestMapping("/category")
public class CategoryController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CreateCategoryForm categoryForm) {
        Category category = categoryForm.toModel(entityManager);
        entityManager.persist(category);

        return ResponseEntity.ok(new CategoryResponse(category));
    }

}
