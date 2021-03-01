package com.zup.mercadolivre.entity.product;

import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.exceptions.UnauthorizedRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductTest {

    private List<ProductImages> productImagesListTest;
    private Product productTest;

    @BeforeEach
    public void setup(){
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
                new User("userTest@zup.com.br", "123456")
        );

        ProductImages productImages = new ProductImages("www.test.com.br", productTest);
        productImagesListTest = new ArrayList<>();
        productImagesListTest.add(productImages);
    }

    @Test
    public void testAddImage() throws UnauthorizedRequest {
        productTest.addImage(productImagesListTest, productTest.getOwner().getLogin());
        Assertions.assertNotNull(productTest.getImages());
        Assertions.assertEquals(productImagesListTest, productTest.getImages());
    }

    @Test
    public void testAddImageWithInvalidUser() throws UnauthorizedRequest {
        UnauthorizedRequest exception = Assertions.assertThrows(
                UnauthorizedRequest.class,
                ()-> productTest.addImage(productImagesListTest, "invalid")
        );
        Assertions.assertEquals("User not authorized to this operation", exception.getMessage());
        Assertions.assertTrue(productTest.getImages().isEmpty());
    }

}
