package com.zup.mercadolivre.controller;

import com.google.gson.Gson;
import com.zup.mercadolivre.entity.category.Category;
import com.zup.mercadolivre.entity.category.response.CategoryResponse;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @ParameterizedTest
    @MethodSource("createCategoryParametersProvider")
    @WithMockUser
    public void testUserCreation(String jsonContent, Boolean isOk) throws Exception {

        MvcResult result = this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(isOk ? status().isOk() : status().isBadRequest())
                .andReturn();

        if (isOk) {
            String content = result.getResponse().getContentAsString();
            CategoryResponse categoryResponse = new Gson().fromJson(content, CategoryResponse.class);
            Category createdCategory = entityManager.find(Category.class, categoryResponse.getId());
            Assertions.assertNotNull(createdCategory);
        }
    }

    private static Stream<Arguments> createCategoryParametersProvider(){
        return Stream.of(
                Arguments.of("{\"name\": \"guilherme.pernicone@zuo.com.br\"}", true),
                Arguments.of("{\"categoryId\": \"1\"}", false),
                Arguments.of("{\"name\": \"guilherme.pernicone\",\"categoryId\": \"999\"}", false)
        );
    }
}
