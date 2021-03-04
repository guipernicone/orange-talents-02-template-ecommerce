package com.zup.mercadolivre.entity.purchase.request;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseGatewayRequest;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class PurchaseGatewayRequestTest {

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    private Purchase purchaseTest;

    @BeforeEach
    public void setup(){
        Category category = new Category("CategoryTest", null);

        User user = new User("test@zup.com.br", "123456");

        Product product=  new Product(
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
                user
        );

        purchaseTest = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.INITIATE,
                user,
                product
        );

        Mockito.when(entityManager.createQuery(ArgumentMatchers.anyString())).thenReturn(query);
        Mockito.when(query.setParameter(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(query);

    }

    @Test
    public void testToModelNewEntity(){
        Mockito.when(query.getResultList()).thenReturn(new ArrayList<>());
        Mockito.when(entityManager.find(Purchase.class, purchaseTest.getId())).thenReturn(purchaseTest);

        PurchaseGatewayRequest purchaseGatewayRequest =  new PurchaseGatewayRequest(
                1,
                purchaseTest.getId(),
                GatewayStatusEnum.ERRO
        );

        GatewayPayment gatewayPayment = purchaseGatewayRequest.toModel(entityManager);
        Assertions.assertNotNull(gatewayPayment);
        Assertions.assertEquals(purchaseGatewayRequest.getGatewayId(), gatewayPayment.getGatewayId());
    }

    @Test
    public void testToModelUpdatedEntity(){
        GatewayPayment gatewayPaymentPrev = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.ERRO);
        Mockito.when(query.getResultList()).thenReturn(Collections.singletonList(gatewayPaymentPrev));
        Mockito.when(entityManager.find(Purchase.class, purchaseTest.getId())).thenReturn(purchaseTest);

        PurchaseGatewayRequest purchaseGatewayRequest =  new PurchaseGatewayRequest(
                1,
                purchaseTest.getId(),
                GatewayStatusEnum.SUCESSO
        );

        GatewayPayment gatewayPayment = purchaseGatewayRequest.toModel(entityManager);
        Assertions.assertNotNull(gatewayPayment);
        Assertions.assertEquals(gatewayPaymentPrev.getGatewayId(), gatewayPayment.getGatewayId());
        Assertions.assertEquals(GatewayStatusEnum.SUCESSO.getValue(), gatewayPayment.getStatus());
    }

    @Test
    public void testToModelError(){
        GatewayPayment gatewayPaymentPrev = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.SUCESSO);
        Mockito.when(query.getResultList()).thenReturn(Collections.singletonList(gatewayPaymentPrev));
        Mockito.when(entityManager.find(Purchase.class, purchaseTest.getId())).thenReturn(purchaseTest);

        PurchaseGatewayRequest purchaseGatewayRequest =  new PurchaseGatewayRequest(
                1,
                purchaseTest.getId(),
                GatewayStatusEnum.ERRO
        );

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> purchaseGatewayRequest.toModel(entityManager)
        );

        Assertions.assertEquals("Transaction already finished", exception.getMessage());
    }
}
