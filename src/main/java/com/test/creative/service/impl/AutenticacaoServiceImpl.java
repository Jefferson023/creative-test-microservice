package com.test.creative.service.impl;

import com.test.creative.domain.Usuario;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements UserDetailsService {
    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final var usuario = usuarioService.getUsuarioPorEmail(username);
            return usuario;
        } catch (RegraNegocioException regraNegocioException) {
            throw new UsernameNotFoundException(regraNegocioException.getMessage());
        }
    }
}
