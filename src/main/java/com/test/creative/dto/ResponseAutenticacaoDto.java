package com.test.creative.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseAutenticacaoDto {
    private final String token;
}
