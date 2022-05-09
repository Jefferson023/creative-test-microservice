package com.test.creative.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.creative.domain.Usuario;
import com.test.creative.dto.RequestUsuarioDto;

public class UsuarioMapper {

    public static Usuario map(RequestUsuarioDto usuarioDto){
        return new ObjectMapper().convertValue(usuarioDto, Usuario.class);
    }
}
