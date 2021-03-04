package com.zup.mercadolivre.entity.purchase;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class GatewayPaymentTest {

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


    }

    @Test
    public void testUpdateStatus(){
        GatewayPayment gatewayPayment = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.ERRO);
        gatewayPayment.updateStatus(GatewayStatusEnum.SUCESSO);
        Assertions.assertEquals(GatewayStatusEnum.SUCESSO.getValue(), gatewayPayment.getStatus());
    }

    @Test
    public void testUpdateStatusException(){
        GatewayPayment gatewayPayment = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.SUCESSO);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> gatewayPayment.updateStatus(GatewayStatusEnum.SUCESSO)
        );

        Assertions.assertEquals("The transaction is already finished and cannot be changed", exception.getMessage());
    }
}
