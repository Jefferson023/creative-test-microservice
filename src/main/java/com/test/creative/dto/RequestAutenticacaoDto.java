package com.test.creative.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Getter
public class RequestAutenticacaoDto {

    @NotEmpty
    private final String email;

    @NotEmpty
    private final String senha;
}
