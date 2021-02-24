package com.zup.mercadolivre.entity.category.form;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.validation.UniqueValue;
import com.zup.mercadolivre.validation.ValidCategoryId;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CreateCategoryForm {

    @NotBlank
    @UniqueValue(uniqueField = "name", targetClass = Category.class)
    private String name;

    @ValidCategoryId
    private String categoryId;

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Category toModel(EntityManager entityManager){
        if(categoryId == null){
            return new Category(name, null);
        }

        Category category = entityManager.find(Category.class, categoryId);
        Assert.notNull(category, "Invalid parent category id");
        return new Category(name, category);
    }
}
