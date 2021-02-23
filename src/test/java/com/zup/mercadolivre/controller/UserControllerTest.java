package com.zup.mercadolivre.controller;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Transactional
    @ParameterizedTest
    @MethodSource("userCreationgParametersProvider")
    public void testUserCreation(String jsonContent, Boolean isOk) throws Exception {

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jsonContent))
                .andExpect(isOk ? status().isOk() : status().isBadRequest());

    }

    private static Stream<Arguments> userCreationgParametersProvider(){
        return Stream.of(
                Arguments.of("{\"login\": \"guilherme.pernicone@zuo.com.br\",\"password\": \"123456\"}", true),
                Arguments.of("{\"login\": \"guilherme.pernicone\",\"password\": \"123456\"}", false),
                Arguments.of("{\"login\": \"guilherme.pernicone@zup.com.br\",\"password\": \"12345\"}", false)
        );

    }


}
