package com.test.creative.dto;

import com.test.creative.domain.enums.Perfil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
public class RequestUsuarioDto {
    private final String id;
    @NotEmpty
    private final String nome;
    @NotEmpty
    private final String email;
    @NotEmpty
    private final String senha;
    private final String endereço;
    private final String telefone;

    @NotNull
    private final Perfil perfil;
}
