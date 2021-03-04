package com.zup.mercadolivre.controller;

import com.google.gson.Gson;
import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.GatewayStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Enum.PurchaseStatusEnum;
import com.zup.mercadolivre.entity.purcharse.Purchase;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseGatewayRequest;
import com.zup.mercadolivre.entity.purcharse.request.PurchaseRequest;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private Product productTest;

    private Purchase purchaseTest;

    @BeforeEach
    public void setup(){
        Category category = new Category("CategoryTest", null);
        entityManager.persist(category);

        User user = new User("test@zup.com.br", "123456");
        entityManager.persist(user);

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
                user
        );
        entityManager.persist(productTest);

        purchaseTest = new Purchase(
                1,
                GatewayEnum.PAYPAL,
                PurchaseStatusEnum.INITIATE,
                user,
                productTest
        );

        entityManager.persist(purchaseTest);
    }
    @Test
    @WithMockUser
    @Transactional
    public void testCreatePurchaseWithInvalidUser() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(this.productTest.getId(), 1, GatewayEnum.PAYPAL);
        this.mockMvc
                .perform(post("/purchase").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(purchaseRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test@zup.com.br", password = "123456")
    @Transactional
    public void testCreatePurchaseWithInvalidStock() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(this.productTest.getId(), 99, GatewayEnum.PAYPAL);
        this.mockMvc
                .perform(post("/purchase").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(purchaseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product out of stock"));
    }

    @Test
    @WithMockUser(username = "test@zup.com.br", password = "123456")
    @Transactional
    public void testCreatePurchase() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest(this.productTest.getId(), 1, GatewayEnum.PAYPAL);
        MvcResult result = this.mockMvc
                .perform(post("/purchase").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(purchaseRequest)))
                .andExpect(status().is(302))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Purchase purchaseCreated = entityManager.find(Purchase.class, Long.parseLong(String.valueOf(content.charAt(11))));
        Assertions.assertNotNull(purchaseCreated);
    }

    @Test
    @WithMockUser
    @Transactional
    public void testGatewayReturnErrorStatus() throws Exception {
        PurchaseGatewayRequest purchaseGatewayRequest = new PurchaseGatewayRequest(
                1,
                purchaseTest.getId(),
                GatewayStatusEnum.ERRO
        );
        this.mockMvc
                .perform(post("/purchase/gateway").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(purchaseGatewayRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(PurchaseStatusEnum.INITIATE.getValue())));
    }

    @Test
    @WithMockUser
    @Transactional
    public void testGatewayReturnSucessoStatus() throws Exception {
        PurchaseGatewayRequest purchaseGatewayRequest = new PurchaseGatewayRequest(
                1,
                purchaseTest.getId(),
                GatewayStatusEnum.SUCESSO
        );
        this.mockMvc
                .perform(post("/purchase/gateway").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(purchaseGatewayRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(PurchaseStatusEnum.FINISHED.getValue())));
    }

}
