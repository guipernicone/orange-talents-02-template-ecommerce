package com.zup.mercadolivre.entity.category.response;

import com.zup.mercadolivre.entity.category.Category;

public class CategoryParentResponse {

    private long id;
    private String name;

    public CategoryParentResponse(Category parentCategory) {
        this.id = parentCategory.getId();
        this.name = parentCategory.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
