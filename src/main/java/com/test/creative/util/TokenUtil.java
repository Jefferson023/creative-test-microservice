package com.test.creative.util;

import com.test.creative.dto.ResponseAutenticacaoDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Date;

public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationSeconds;

    public ResponseAutenticacaoDto createResponseAutenticacao(Authentication authentication) {
        final var token = gerarToken(authentication);
        return new ResponseAutenticacaoDto(token);
    }

    private String gerarToken(Authentication authentication) {

        final var user = (UserDetails) authentication.getPrincipal();

        final var dataAtual = new Date();
        final var dataExpiracao = Date.from(dataAtual.toInstant().plusSeconds(expirationSeconds));
        return Jwts.builder().setIssuer("Creative")
                .setSubject(user.getUsername())
                .setIssuedAt(dataAtual)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String getTokenFromRequest(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }

    private String getUserEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getAutenticacaoUsernameFromToken(String bearerToken) {
        final var token = getTokenFromRequest(bearerToken);
        if (isTokenValid(token)) {
            final var email = getUserEmailFromToken(token);
            return email;
        }else {
            return null;
        }
    }
}
