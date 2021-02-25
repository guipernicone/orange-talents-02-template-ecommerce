package com.zup.mercadolivre.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.request.CreateProductCharacteristicsRequest;
import com.zup.mercadolivre.entity.product.request.CreateProductRequest;
import org.junit.jupiter.api.Assertions;
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
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @WithMockUser
    @Test
    public void testCreateProductOkResponse() throws Exception {
        Category category = new Category("CategoryTest", null);
        entityManager.persist(category);

        CreateProductRequest productRequest = new CreateProductRequest(
                "testName",
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value"),
                        new CreateProductCharacteristicsRequest("detailName", "value")
                ),
                "description",
                String.valueOf(category.getId())
        );

        MvcResult result = this.mockMvc
                .perform(
                        post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(productRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode productResponse = new ObjectMapper().readTree(content);
        Product createdProduct = entityManager.find(Product.class, Long.parseLong(String.valueOf(productResponse.get("id"))));
        Assertions.assertNotNull(createdProduct);

    }

    @Transactional
    @WithMockUser
    @Test
    public void testCreateProductBadRequestResponse() throws Exception {
        CreateProductRequest productRequest = new CreateProductRequest(
                null,
                new BigDecimal(1),
                1,
                new ArrayList<>(),
                "description",
                "1"
        );

        this.mockMvc
                .perform(
                        post("/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(productRequest))
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
