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

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootTest
public class ValidPurchaseIdValidatorTest {
    @Mock
    EntityManager entityManager;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @InjectMocks
    ValidPurchaseIdValidator validPurchaseIdValidator;

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
    public void testIsValidNullPurchase(){
        Mockito.when(entityManager.find(Purchase.class, (long) 1)).thenReturn(null);
        Assertions.assertFalse(validPurchaseIdValidator.isValid("1",  mockConstraintValidatorContext));
    }

    @Test
    public void testIsValidStatusNotFinished(){
        Purchase purchase = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.INITIATE,
                userTest,
                productTest
        );
        Mockito.when(entityManager.find(Purchase.class, (long) 1)).thenReturn(purchase);
        Assertions.assertTrue(validPurchaseIdValidator.isValid("1",  mockConstraintValidatorContext));
    }

    @Test
    public void testIsValidStatusFinished(){
        Purchase purchase = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.FINISHED,
                userTest,
                productTest
        );
        Mockito.when(entityManager.find(Purchase.class, (long) 1)).thenReturn(purchase);
        Assertions.assertFalse(validPurchaseIdValidator.isValid("1",  mockConstraintValidatorContext));
    }
}
