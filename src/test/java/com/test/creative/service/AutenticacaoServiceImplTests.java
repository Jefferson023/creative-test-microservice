package com.test.creative.service;

import com.test.creative.exception.RegraNegocioException;
import com.test.creative.service.impl.AutenticacaoServiceImpl;
import com.test.creative.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AutenticacaoServiceImplTests {

    @InjectMocks
    private AutenticacaoServiceImpl autenticacaoService;

    @Mock
    private UsuarioServiceImpl usuarioService;

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = { "teste@email.com", "teste.teste@email.net"})
    public void loadUserByUsernameShouldThrowExceptionWhenUserNotFound(String email) {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            Mockito.when(usuarioService.getUsuarioPorEmail(Mockito.anyString())).thenThrow(new RegraNegocioException(""));
            autenticacaoService.loadUserByUsername(email);
        });
    }
}
