package com.test.creative.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationUtilTests {

    @ParameterizedTest(name = " {0} nao e um email valido")
    @ValueSource(strings = { "teste.com", "teste"})
    public void emailIsValidoShouldReturnFalseWhenEmailIsNotValid(String email){
        assertEquals(false, ValidationUtil.emailIsValido(email));
    }

    @ParameterizedTest(name = " {0} e um email valido")
    @ValueSource(strings = { "teste@email.com", "teste.teste@email.net"})
    public void emailIsValidoShouldReturnTrueWhenEmailIsValid(String email){
        assertEquals(true, ValidationUtil.emailIsValido(email));
    }
}
