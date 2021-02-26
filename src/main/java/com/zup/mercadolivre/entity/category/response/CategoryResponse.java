package com.zup.mercadolivre.entity.category.response;

import com.zup.mercadolivre.entity.category.Category;

public class CategoryResponse {

    private long id;
    private String name;
    private CategoryParentResponse parentCategory;

    public CategoryResponse(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.parentCategory = null;
        if (category.getParentCategory() != null){
            this.parentCategory = new CategoryParentResponse(category.getParentCategory());
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryParentResponse getParentCategory() {
        return parentCategory;
    }
}
