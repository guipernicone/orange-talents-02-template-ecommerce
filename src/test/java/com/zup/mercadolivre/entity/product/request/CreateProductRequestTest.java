package com.zup.mercadolivre.entity.product.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Arrays;


@SpringBootTest
@ActiveProfiles("test")
public class CreateProductRequestTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private Query query;

    private User userTest;
    private Category categoryTest;
    private CreateProductRequest productRequestTest;

    @BeforeEach
    public void Setup(){
        this.productRequestTest = new CreateProductRequest(
                "productTest",
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value")
                ),
                "description",
                "1"
        );
       userTest = new User("test@zup.com.br", "123456");
       categoryTest = new Category("test", null);
    }

    @Test
    public void testToModelCategoryValidId(){
        Mockito.when(
                mockEntityManager.find(Category.class,
                Long.parseLong(productRequestTest.getCategoryId()))
        ).thenReturn(categoryTest);
        Mockito.when(mockEntityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.setParameter(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(userTest);

        Product product = productRequestTest.toModel(mockEntityManager, userTest.getLogin());

        Assertions.assertNotNull(product.getCategory());
        Assertions.assertNotNull(product.getProductCharacteristics());
        Assertions.assertEquals(productRequestTest.getName(), product.getName());
        Assertions.assertNotNull(product.getOwner());
        Assertions.assertEquals(userTest.getLogin(),product.getOwner().getLogin());

    }

    @Test
    public void testToModelCategoryNotValidId(){
        Mockito.when(mockEntityManager.find(
                Category.class,
                Long.parseLong(productRequestTest.getCategoryId()))
        ).thenReturn(null);
        Mockito.when(mockEntityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.setParameter(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(userTest);

        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    productRequestTest.toModel(mockEntityManager, userTest.getLogin());
                });
        Assertions.assertEquals(
                "The category with id (" +productRequestTest.getCategoryId() +") was not found",
                exception.getMessage()
        );

    }

    @Test
    public void testToModelUserNotValidId(){ ;
        Mockito.when(mockEntityManager.find(
                Category.class,
                Long.parseLong(productRequestTest.getCategoryId()))
        ).thenReturn(categoryTest);
        Mockito.when(mockEntityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.setParameter(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(null);

        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    productRequestTest.toModel(mockEntityManager, userTest.getLogin());
                });
        Assertions.assertEquals("Invalid user id", exception.getMessage());
    }
}
