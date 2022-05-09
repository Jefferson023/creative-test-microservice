package com.test.creative.service;

import com.test.creative.domain.Usuario;
import com.test.creative.dto.RequestUsuarioDto;
import com.test.creative.exception.RegraNegocioException;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {
    public Page<Usuario> buscaUsuarioComFiltro(String nome, String email, String telefone, String endereco, int page, int size,
                                               Collection<String> sortBy, String order);
    public Usuario criarUsuario(RequestUsuarioDto usuarioDto) throws RegraNegocioException;
    public void removerUsuario(String usuarioId) throws RegraNegocioException;
    public Usuario atualizarUsuario(RequestUsuarioDto usuarioDto) throws RegraNegocioException;
    public Usuario getUsuario(String usuarioId) throws RegraNegocioException;
    public Optional<Usuario> buscarUsuario(String usuarioId);
    public Usuario getUsuarioPorEmail(String email) throws RegraNegocioException;
}
