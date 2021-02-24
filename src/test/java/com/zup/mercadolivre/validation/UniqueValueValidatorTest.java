package com.zup.mercadolivre.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UniqueValueValidatorTest {

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @Mock
    private Query mockQuery;

    @InjectMocks
    private UniqueValueValidator uniqueValueValidator;

    @BeforeEach
    public void setup() {
        when(mockEntityManager.createQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), anyString())).thenReturn(mockQuery);
        UniqueValue uniqueValue = uniqueValueTestClass(Object.class);
        uniqueValueValidator.initialize(uniqueValue);
    }
    @Test
    public void testIsValidFalse(){
        when(mockQuery.setParameter(anyString(), anyString())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(Collections.singletonList(1));
        assertFalse(uniqueValueValidator.isValid("value", mockConstraintValidatorContext));
    }

    @Test
    public void testIsValidTrue(){
        when(mockQuery.setParameter(anyString(), anyString())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());
        assertTrue(uniqueValueValidator.isValid("value", mockConstraintValidatorContext));
    }

    private UniqueValue uniqueValueTestClass(Class<?> targetClass) {
     return new UniqueValue() {
            @Override
            public String message () {
                return "Test Message";
            }

            @Override
            public Class<?>[] groups () {
                return new Class[]{};
            }

            @Override
            public Class<? extends Payload>[] payload () {
                return new Class[0];
            }


            @Override
            public String uniqueField () {
                return null;
            }

            @Override
            public Class<?> targetClass () {
                return targetClass;
            }

            @Override
            public Class<? extends Annotation> annotationType () {
                return UniqueValue.class;
            }
        };
    }
}
