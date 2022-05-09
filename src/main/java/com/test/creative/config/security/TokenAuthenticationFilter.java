package com.test.creative.config.security;

import com.test.creative.domain.Usuario;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.service.UsuarioService;
import com.test.creative.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final var usuario = getUsuarioFromRequest(request);

        if (usuario != null) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private Usuario getUsuarioFromRequest(HttpServletRequest request){
        final var emailUsuario = tokenUtil.getAutenticacaoUsernameFromToken(request.getHeader("Authorization"));
        try {
            final var usuario = usuarioService.getUsuarioPorEmail(emailUsuario);
            return usuario;
        } catch (RegraNegocioException regraNegocioException) {
            return null;
        }
    }
}
