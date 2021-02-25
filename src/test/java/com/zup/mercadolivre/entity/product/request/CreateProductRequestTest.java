package com.zup.mercadolivre.entity.product.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.response.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CreateProductRequestTest {

    @Mock
    private EntityManager mockEntityManager;

    @Test
    public void testToModelCategoryValidId(){
        String productName = "productTest";
        String categoryId = "1";
        CreateProductRequest productRequest = new CreateProductRequest(
                productName,
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value")
                ),
                "description",
                categoryId
        );

        Category category = new Category("test", null);
        when(mockEntityManager.find(Category.class, Long.parseLong(categoryId))).thenReturn(category);

        Product product = productRequest.toModel(mockEntityManager);
        Assertions.assertNotNull(product.getCategory());
        Assertions.assertNotNull(product.getProductCharacteristics());
        Assertions.assertEquals(productName, product.getName());

    }

    @Test
    public void testToModelCategoryNotValidId(){
        String productName = "productTest";
        String categoryId = "1";
        CreateProductRequest productRequest = new CreateProductRequest(
                productName,
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value")
                ),
                "description",
                categoryId
        );

        Category category = new Category("test", null);
        when(mockEntityManager.find(Category.class, Long.parseLong(categoryId))).thenReturn(null);

        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    productRequest.toModel(mockEntityManager);
                });
        Assertions.assertEquals("The category with id (" +categoryId+") was not found", exception.getMessage());
    }
}
