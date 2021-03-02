package com.zup.mercadolivre.entity.opinion.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootTest
public class OpinionRequestTest {

    @Mock
    private EntityManager mockEntityManager;

    private Product productTest;
    private User userTest;
    private OpinionRequest opinionRequest;

    @BeforeEach
    public void setup(){
        userTest = new User("userTest@zup.com.br", "123456");
        productTest = new Product(
                "productTest",
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value")
                ),
                "description",
                new Category("categoryTest", null),
                userTest
        );
        opinionRequest = new OpinionRequest(
                "opinionTitle",
                "5",
                "this is a opinion test",
                productTest.getId()
        );
    }
    @Test
    public void testToModel(){
        System.out.println(mockEntityManager);
        Mockito.when(mockEntityManager.find(Product.class, opinionRequest.getProductId())).thenReturn(productTest);
        Opinion opinion = opinionRequest.toModel(mockEntityManager, userTest);
        Assertions.assertNotNull(opinion);
        Assertions.assertEquals(opinionRequest.getTitle(), opinion.getTitle());
    }

    @Test
    public void testToModelWithInvalidUser(){
        Mockito.when(mockEntityManager.find(Product.class, opinionRequest.getProductId())).thenReturn(productTest);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->opinionRequest.toModel(mockEntityManager, null)
        );
        Assertions.assertEquals("Invalid user", exception.getMessage());
    }

    @Test
    public void testToModelWithInvalidProduct(){
        Mockito.when(mockEntityManager.find(Product.class, opinionRequest.getProductId())).thenReturn(null);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->opinionRequest.toModel(mockEntityManager, userTest)
        );
        Assertions.assertEquals("Invalid product", exception.getMessage());
    }

}
