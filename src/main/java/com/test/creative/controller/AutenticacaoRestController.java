package com.test.creative.controller;

import com.test.creative.dto.RequestAutenticacaoDto;
import com.test.creative.dto.ResponseAutenticacaoDto;
import com.test.creative.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AutenticacaoRestController {

    private final TokenUtil tokenUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseAutenticacaoDto autenticar(@Valid @RequestBody RequestAutenticacaoDto autenticacaoDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(autenticacaoDto.getEmail(), autenticacaoDto.getSenha());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return tokenUtil.createResponseAutenticacao(authentication);
    }
}
