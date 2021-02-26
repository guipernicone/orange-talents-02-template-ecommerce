package com.zup.mercadolivre.entity.category.response;

import com.zup.mercadolivre.entity.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class CategoryResponseTest {

    @Test
    public void testConstructorParentCountryNotNull(){
        String categoryName = "categoryName";
        Category parentCategory = new Category("parentCategory", null);
        Category category = new Category(categoryName, parentCategory);

        CategoryResponse categoryResponse = new CategoryResponse(category);

        Assertions.assertEquals(categoryName, categoryResponse.getName());
        Assertions.assertNotNull(category.getParentCategory());
    }

    @Test
    public void testConstructorParentCountryNull(){
        String categoryName = "categoryName";
        Category category = new Category(categoryName, null);

        CategoryResponse categoryResponse = new CategoryResponse(category);

        Assertions.assertEquals(categoryName, categoryResponse.getName());
        Assertions.assertNull(category.getParentCategory());
    }
}
