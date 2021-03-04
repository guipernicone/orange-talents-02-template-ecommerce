package com.zup.mercadolivre.entity.purchase;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class PurchaseTest {

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
    @ParameterizedTest
    @MethodSource("testGenerateUrlProvider")
    public void testGenerateUrl(String expectedResponse, GatewayEnum gateway){
        Purchase purchaseTest = new Purchase(
                1,
                gateway,
                PurchaseStatusEnum.INITIATE,
                userTest,
                productTest
        );

        String response = purchaseTest.generateURL();
        Assertions.assertEquals(expectedResponse, response);
    }

    public static Stream<Arguments> testGenerateUrlProvider(){
        return Stream.of(
                Arguments.of("paypal.com/0?redirectUrl=urlRetornoAppPosPagamento", GatewayEnum.PAYPAL),
                Arguments.of("pagseguro.com?returnId=0&redirectUrl=urlRetornoAppPosPagamento", GatewayEnum.PAGSEGURO)
        );
    }

    @Test
    public void testUpdateStatus(){
        Purchase purchaseTest = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.INITIATE,
                userTest,
                productTest
        );
        purchaseTest.updateStatus(PurchaseStatusEnum.FINISHED);

        Assertions.assertEquals(PurchaseStatusEnum.FINISHED.getValue(), purchaseTest.getStatus());
    }

    @Test
    public void testUpdateStatusException(){
        Purchase purchaseTest = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.FINISHED,
                userTest,
                productTest
        );
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> purchaseTest.updateStatus(PurchaseStatusEnum.FINISHED)
        );

        Assertions.assertEquals("This purchase is already finished and cannot be altered", exception.getMessage());
    }
}
