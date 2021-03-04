package com.zup.mercadolivre.entity.purchase.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseRequest;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootTest
public class PurchaseRequestTest {

    @Mock
    EntityManager entityManager;

    private User userTest;
    private Product productTest;

    @BeforeEach
    public void setup(){
        Category category = new Category("CategoryTest", null);

        userTest = new User("test@zup.com.br", "123456");

        productTest =  new Product(
                "productTest",
                new BigDecimal(1),
                10,
                Arrays.asList(
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value")
                ),
                "description",
                category,
                userTest
        );

    }

    @Test
    public void testToModel(){
        PurchaseRequest purchaseRequest = new PurchaseRequest(productTest.getId(), 1, GatewayEnum.PAYPAL);

        Mockito.when(entityManager.find(Product.class, purchaseRequest.getProductId())).thenReturn(productTest);

        Purchase purchase = purchaseRequest.toModel(entityManager, userTest);

        Assertions.assertNotNull(purchase);
        Assertions.assertEquals(PurchaseStatusEnum.INITIATE.getValue(), purchase.getStatus());
        Assertions.assertNotNull(purchase.getProduct());
        Assertions.assertEquals(9, purchase.getProduct().getInventory());
    }

    @Test
    public void testToModelOutOfStock(){
        PurchaseRequest purchaseRequest = new PurchaseRequest(productTest.getId(), 11, GatewayEnum.PAYPAL);

        Mockito.when(entityManager.find(Product.class, purchaseRequest.getProductId())).thenReturn(productTest);

        Purchase purchase = purchaseRequest.toModel(entityManager, userTest);

        Assertions.assertNotNull(purchase);
        Assertions.assertEquals(PurchaseStatusEnum.NOT_INITIATE.getValue(), purchase.getStatus());
        Assertions.assertNotNull(purchase.getProduct());
        Assertions.assertEquals(10, purchase.getProduct().getInventory());
    }

    @Test
    public void testToModelInvalidUser(){
        PurchaseRequest purchaseRequest = new PurchaseRequest(productTest.getId(), 1, GatewayEnum.PAYPAL);

        Mockito.when(entityManager.find(Product.class, purchaseRequest.getProductId())).thenReturn(productTest);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> purchaseRequest.toModel(entityManager, null)
        );

        Assertions.assertEquals("The user must not be null", exception.getMessage());
    }

    @Test
    public void testToModelInvalidProductr(){
        PurchaseRequest purchaseRequest = new PurchaseRequest(productTest.getId(), 1, GatewayEnum.PAYPAL);

        Mockito.when(entityManager.find(Product.class, purchaseRequest.getProductId())).thenReturn(null);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> purchaseRequest.toModel(entityManager, userTest)
        );

        Assertions.assertEquals("The product must not be null", exception.getMessage());
    }
}
