package com.zup.mercadolivre.entity.question.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.opinion.request.OpinionRequest;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootTest
public class CreateQuestionRequestTest {

    @Mock
    private EntityManager mockEntityManager;

    private Product productTest;
    private User userTest;
    private CreateQuestionRequest questionRequest;

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
        questionRequest = new CreateQuestionRequest(
                "questionTitle",
                "this is a quetion test",
                productTest.getId()
        );
    }
    @Test
    public void testToModel(){
        Mockito.when(mockEntityManager.find(Product.class, questionRequest.getProductId())).thenReturn(productTest);
        Question question = questionRequest.toModel(mockEntityManager, userTest);
        Assertions.assertNotNull(question);
        Assertions.assertEquals(questionRequest.getTitle(), question.getTitle());
    }

    @Test
    public void testToModelWithInvalidUser(){
        Mockito.when(mockEntityManager.find(Product.class, questionRequest.getProductId())).thenReturn(productTest);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->questionRequest.toModel(mockEntityManager, null)
        );
        Assertions.assertEquals("Invalid user", exception.getMessage());
    }

    @Test
    public void testToModelWithInvalidProduct(){
        Mockito.when(mockEntityManager.find(Product.class, questionRequest.getProductId())).thenReturn(null);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->questionRequest.toModel(mockEntityManager, userTest)
        );
        Assertions.assertEquals("Invalid product id", exception.getMessage());
    }
}
