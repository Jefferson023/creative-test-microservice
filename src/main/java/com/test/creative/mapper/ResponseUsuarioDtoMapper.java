package com.test.creative.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.creative.domain.Usuario;
import com.test.creative.dto.ResponseUsuarioDto;

public class ResponseUsuarioDtoMapper {
    public static ResponseUsuarioDto map(Usuario usuario){
        return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .convertValue(usuario, ResponseUsuarioDto.class);
    }
}
