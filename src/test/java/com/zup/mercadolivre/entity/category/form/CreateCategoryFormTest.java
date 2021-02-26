package com.zup.mercadolivre.entity.category.form;

import com.zup.mercadolivre.entity.category.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateCategoryFormTest {

    @Mock
    private EntityManager mockEntityManager;

    @Test
    public void testToModelCategoryIdNotNull(){
        String categoryName = "categoryName";
        String categoryParentName = "categoryParentName";
        String categoryId = "1";

        Category category = new Category(categoryParentName, null);
        when(mockEntityManager.find(Category.class, Long.parseLong(categoryId))).thenReturn(category);

        CreateCategoryForm categoryForm = new CreateCategoryForm(categoryName, categoryId);
        Category categoryResponse = categoryForm.toModel(mockEntityManager);

        Assertions.assertNotNull(categoryResponse.getParentCategory());
        Assertions.assertEquals(categoryName, categoryResponse.getName());
    }

    @Test
    public void testToModelCategoryIdNull(){
        String categoryName = "categoryName";

        CreateCategoryForm categoryForm = new CreateCategoryForm(categoryName, null);
        Category categoryResponse = categoryForm.toModel(mockEntityManager);

        Assertions.assertNull(categoryResponse.getParentCategory());
        Assertions.assertEquals(categoryName, categoryResponse.getName());
    }

    @Test
    public void testToModelCategoryIdInvalid(){
        String categoryName = "categoryName";
        String categoryId = "1";

        when(mockEntityManager.find(Category.class, categoryId)).thenReturn(null);
        CreateCategoryForm categoryForm = new CreateCategoryForm(categoryName, categoryId);


        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    categoryForm.toModel(mockEntityManager);
                });
        Assertions.assertEquals("Invalid parent category id", exception.getMessage());
    }

}
