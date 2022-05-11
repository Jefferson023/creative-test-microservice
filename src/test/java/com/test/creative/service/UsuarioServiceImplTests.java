package com.test.creative.service;

import com.test.creative.domain.Usuario;
import com.test.creative.domain.enums.Perfil;
import com.test.creative.dto.RequestUsuarioDto;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.repository.UsuarioRepository;
import com.test.creative.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTests {
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    static Stream<RequestUsuarioDto> requestUsuarioDtoProvider(){
        final var requestUsuarioDto1 = new RequestUsuarioDto("1", "teste", "teste@teste.com", "123", "", "", Perfil.USER);
        final var requestUsuarioDto2 = new RequestUsuarioDto("2", "teste2", "teste2@teste.com", "123", "", "", Perfil.USER);
        return Stream.of(requestUsuarioDto1, requestUsuarioDto2);
    }

    @ParameterizedTest
    @MethodSource("requestUsuarioDtoProvider")
    public void criarUsuarioShouldThrowRegraNegocioExceptionWhenEmailIsNotLivre(RequestUsuarioDto requestUsuarioDto){
        final var usuario = Usuario.builder().id("").email("").nome("").senha("").endereço("").telefone("").perfil(Perfil.USER).build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Assertions.assertThrows(RegraNegocioException.class, () -> {
           usuarioService.criarUsuario(requestUsuarioDto);
        });
    }

    static Stream<RequestUsuarioDto> requestUsuarioDtoEmailErradoProvider(){
        final var requestUsuarioDto1 = new RequestUsuarioDto("1", "teste", "teste.com", "123", "", "", Perfil.USER);
        final var requestUsuarioDto2 = new RequestUsuarioDto("2", "teste2", "com", "123", "", "", Perfil.USER);
        return Stream.of(requestUsuarioDto1, requestUsuarioDto2);
    }

    @ParameterizedTest
    @MethodSource("requestUsuarioDtoEmailErradoProvider")
    public void criarUsuarioShouldThrowRegraNegocioExceptionWhenEmailIsNotValid(RequestUsuarioDto requestUsuarioDto){
        Assertions.assertThrows(RegraNegocioException.class, () -> {
            usuarioService.criarUsuario(requestUsuarioDto);
        });
    }

    @ParameterizedTest
    @MethodSource("requestUsuarioDtoProvider")
    public void criarUsuarioShouldReturnUsuarioWhenUsuarioIsValid(RequestUsuarioDto requestUsuarioDto) throws RegraNegocioException {
        final var usuario = Usuario.builder()
                .email(requestUsuarioDto.getEmail())
                .nome(requestUsuarioDto.getNome())
                .senha(requestUsuarioDto.getSenha())
                .endereço(requestUsuarioDto.getEndereço())
                .telefone(requestUsuarioDto.getTelefone())
                .perfil(requestUsuarioDto.getPerfil())
                .build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        final var expectedUsuario = usuarioService.criarUsuario(requestUsuarioDto);

        Assertions.assertEquals(usuario.getEmail(), expectedUsuario.getEmail(), "O email do usuario deve ser igual");
        Assertions.assertEquals(usuario.getNome(), expectedUsuario.getNome(), "O nome do usuário deve ser igual");
        Assertions.assertEquals(usuario.getEndereço(), expectedUsuario.getEndereço(), "O endereço do usuário deve ser igual");
        Assertions.assertEquals(usuario.getTelefone(), expectedUsuario.getTelefone(), "O telefone do usuário deve ser igual");
        Assertions.assertEquals(usuario.getPerfil().getValue(), expectedUsuario.getPerfil().getValue(), "O perfil do usuário deve ser igual");
    }
}
