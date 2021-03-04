package com.zup.mercadolivre.validation;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.repository.GatewayPaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ValidGatewayTransactionValidatorTest {
    @Mock
    GatewayPaymentRepository mockGatewayPaymentRepository;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @InjectMocks
    ValidGatewayTransactionValidator validGatewayTransactionValidator;


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
    public void testIsValid(){
        Optional<GatewayPayment> gatewayPaymentOptional = Optional.empty();
        Mockito.when(mockGatewayPaymentRepository.findByGatewayId(1)).thenReturn(gatewayPaymentOptional);
        Assertions.assertTrue(validGatewayTransactionValidator.isValid("1",  mockConstraintValidatorContext));
    }

    @Test
    public void testIsValidStatusErro(){
        GatewayPayment gatewayPayment = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.ERRO);
        Optional<GatewayPayment> gatewayPaymentOptional = Optional.of(gatewayPayment);
        Mockito.when(mockGatewayPaymentRepository.findByGatewayId(1)).thenReturn(gatewayPaymentOptional);
        Assertions.assertTrue(validGatewayTransactionValidator.isValid("1",  mockConstraintValidatorContext));
    }

    @Test
    public void testIsValidStatusSucesso(){
        GatewayPayment gatewayPayment = new GatewayPayment(1, purchaseTest, GatewayStatusEnum.SUCESSO);
        Optional<GatewayPayment> gatewayPaymentOptional = Optional.of(gatewayPayment);
        Mockito.when(mockGatewayPaymentRepository.findByGatewayId(1)).thenReturn(gatewayPaymentOptional);
        Assertions.assertFalse(validGatewayTransactionValidator.isValid("1",  mockConstraintValidatorContext));
    }
}
