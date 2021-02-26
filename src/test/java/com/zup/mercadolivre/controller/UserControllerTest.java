package com.zup.mercadolivre.controller;


import com.google.gson.Gson;
import com.zup.mercadolivre.entity.user.User;
import com.zup.mercadolivre.entity.user.response.UserCreateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @ParameterizedTest
    @MethodSource("userCreationgParametersProvider")
    @WithMockUser
    public void testUserCreation(String jsonContent, Boolean isOk) throws Exception {

        MvcResult result = this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(isOk ? status().isOk() : status().isBadRequest())
                .andReturn();

        if (isOk) {
            String content = result.getResponse().getContentAsString();
            UserCreateResponse userResponse = new Gson().fromJson(content,UserCreateResponse.class);
            User createdUser = entityManager.find(User.class, userResponse.getId());
            Assertions.assertNotNull(createdUser);
        }

    }

    private static Stream<Arguments> userCreationgParametersProvider() {
        return Stream.of(
                Arguments.of("{\"login\": \"guilherme.pernicone@zuo.com.br\",\"password\": \"123456\"}", true),
                Arguments.of("{\"login\": \"guilherme.pernicone\",\"password\": \"123456\"}", false),
                Arguments.of("{\"login\": \"guilherme.pernicone@zup.com.br\",\"password\": \"12345\"}", false)
        );
    }


}
