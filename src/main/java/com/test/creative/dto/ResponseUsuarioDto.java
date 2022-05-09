package com.test.creative.dto;

import com.test.creative.domain.enums.Perfil;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUsuarioDto {
    private String id;
    private String nome;
    private String email;
    private String endereço;
    private String telefone;

    private Perfil perfil;
}
