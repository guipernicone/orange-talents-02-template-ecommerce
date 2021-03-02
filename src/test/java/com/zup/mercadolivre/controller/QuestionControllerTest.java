package com.zup.mercadolivre.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.opinion.request.OpinionRequest;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductCharacteristics;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.question.request.CreateQuestionRequest;
import com.zup.mercadolivre.entity.question.response.QuestionResponse;
import com.zup.mercadolivre.entity.user.User;
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
import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @WithMockUser(username = "test@zup.com.br", password = "123456")
    @Test
    public void testCreateQuestion() throws Exception {
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

        CreateQuestionRequest questionRequest = new CreateQuestionRequest(
                "questionTitle",
                "this is a question test",
                product.getId()
        );

        MvcResult result = this.mockMvc
                .perform(
                        post("/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(questionRequest))
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JsonNode questionResponse = new ObjectMapper().readTree(content);
        Question questionCreated = entityManager.find(Question.class, Long.parseLong(String.valueOf(questionResponse.get("id"))));
        Assertions.assertNotNull(questionCreated);
        Assertions.assertNotNull(questionCreated.getProduct());
        Assertions.assertNotNull(questionCreated.getUser());
        Assertions.assertEquals("questionTitle",questionCreated.getTitle());
    }

    @Transactional
    @WithMockUser
    @Test
    public void testCreateQuestionBadRequest() throws Exception {
        CreateQuestionRequest questionRequest = new CreateQuestionRequest(
                null,
                "this is a opinion test",
                1
        );

        this.mockMvc
                .perform(
                        post("/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(questionRequest))
                )
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @WithMockUser
    @Test
    public void testCreateQuestionInternalServerError() throws Exception {
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

        CreateQuestionRequest questionRequest = new CreateQuestionRequest(
                "Test",
                "this is a question test",
                product.getId()
        );

        this.mockMvc
                .perform(
                        post("/question")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(questionRequest))
                )
                .andExpect(status().isInternalServerError());
    }
}
