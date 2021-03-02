package com.zup.mercadolivre.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zup.mercadolivre.configuration.security.service.AuthenticationService;
import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.opinion.request.OpinionRequest;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OpinionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @WithMockUser(username = "test@zup.com.br", password = "123456")
    @Test
    public void testCreateOpinion() throws Exception {
        Category category = new Category("CategoryTest", null);
        entityManager.persist(category);

        User user = new User("test@zup.com.br", "123456");
        entityManager.persist(user);

        Product product =  new Product(
                "productTest",
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value")
                ),
                "description",
                category,
                user
        );
        entityManager.persist(product);

        OpinionRequest opinionRequest = new OpinionRequest(
                "opinionTitle",
                "5",
                "this is a opinion test",
                product.getId()
        );

        MvcResult result = this.mockMvc
                .perform(
                        post("/opinion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(opinionRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode opinionResponse = new ObjectMapper().readTree(content);
        Opinion opinionCreated = entityManager.find(Opinion.class, Long.parseLong(String.valueOf(opinionResponse.get("id"))));
        Assertions.assertNotNull(opinionCreated);
        Assertions.assertNotNull(opinionCreated.getProduct());
        Assertions.assertNotNull(opinionCreated.getProduct());
        Assertions.assertEquals("opinionTitle",opinionCreated.getTitle());
    }

    @Transactional
    @WithMockUser
    @Test
    public void testCreateOpinionBadRequest() throws Exception {
        User user = new User("test@zup.com.br", "123456");
        entityManager.persist(user);

        OpinionRequest opinionRequest = new OpinionRequest(
                null,
                "5",
                "this is a opinion test",
                1
        );

        this.mockMvc
                .perform(
                        post("/opinion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(opinionRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithMockUser
    @Test
    public void testCreateOpinionInternalServerError() throws Exception {
        Category category = new Category("CategoryTest", null);
        entityManager.persist(category);

        User user = new User("test@zup.com.br", "123456");
        entityManager.persist(user);

        Product product =  new Product(
                "productTest",
                new BigDecimal(1),
                1,
                Arrays.asList(
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value"),
                        new ProductCharacteristics("detailName", "value")
                ),
                "description",
                category,
                user
        );
        entityManager.persist(product);

        OpinionRequest opinionRequest = new OpinionRequest(
                "Test",
                "5",
                "this is a opinion test",
                product.getId()
        );

        this.mockMvc
                .perform(
                        post("/opinion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(opinionRequest))
                )
                .andExpect(status().isInternalServerError());
    }
}
