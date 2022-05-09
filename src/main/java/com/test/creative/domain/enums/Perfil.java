package com.test.creative.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Perfil {
    ADMIN("ADMIN"), USER("USER");

    private final String value;

}
