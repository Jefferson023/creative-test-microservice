package com.test.creative.service.impl;

import com.test.creative.domain.Usuario;
import com.test.creative.dto.RequestUsuarioDto;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.mapper.ResponseUsuarioDtoMapper;
import com.test.creative.mapper.UsuarioMapper;
import com.test.creative.repository.UsuarioRepository;
import com.test.creative.service.UsuarioService;
import com.test.creative.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> buscaUsuarioComFiltro(String nome, String email, String telefone, String endereco, int page, int size, Collection<String> sortBy, String order) {
        final var direction = Sort.Direction.fromString(order);
        final var orders = sortBy.stream()
                                        .map(sortField -> new Sort.Order(direction, sortField))
                                        .collect(Collectors.toList());
        final var pageable =  PageRequest.of(page, size, Sort.by(orders));
        return usuarioRepository.findAllByFiltroPaginado(nome, email, endereco,telefone, pageable);
    }

    @Override
    public Usuario criarUsuario(RequestUsuarioDto usuarioDto) throws RegraNegocioException {
        if (!ValidationUtil.emailIsValido(usuarioDto.getEmail())){
            throw new RegraNegocioException("O email do usuário é inválido");
        }
        if (!checaEmailIsLivre(usuarioDto.getEmail())){
            throw new RegraNegocioException("O email do usuário já está sendo utilizado");
        }

        var usuario = UsuarioMapper.map(usuarioDto);
        usuario.setSenha(criptografarSenha(usuarioDto.getSenha()));

        return usuarioRepository.save(usuario);
    }

    private boolean checaEmailIsLivre(String email){
        final var usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public void removerUsuario(String usuarioId) throws RegraNegocioException {
        final var usuario = buscarUsuario(usuarioId)
                                    .orElseThrow(() -> new RegraNegocioException("Id de usuário inválido ou não existe"));
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario atualizarUsuario(RequestUsuarioDto usuarioDto) throws RegraNegocioException {
        final var usuario = buscarUsuario(usuarioDto.getId())
                                    .orElseThrow(() -> new RegraNegocioException("Id de usuário inválido ou não existe"));
        if (!checaEmailIsLivre(usuarioDto.getEmail()) && !usuario.getEmail().equals(usuarioDto.getEmail())){
            throw new RegraNegocioException("O email do usuário já está sendo utilizado");
        }
        final var novoUsuario = Usuario.builder()
                                        .id(usuario.getId())
                                        .nome(usuarioDto.getNome())
                                        .email(usuarioDto.getEmail())
                                        .senha(criptografarSenha(usuarioDto.getSenha()))
                                        .endereço(usuarioDto.getEndereço())
                                        .telefone(usuarioDto.getTelefone())
                                        .perfil(usuarioDto.getPerfil())
                                        .build();
        return usuarioRepository.save(novoUsuario);
    }

    @Override
    public Usuario getUsuario(String usuarioId) throws RegraNegocioException {
        return buscarUsuario(usuarioId).orElseThrow(() -> new RegraNegocioException("Id de usuário inválido ou não existe"));
    }
    @Override
    public Optional<Usuario> buscarUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }

    @Override
    public Usuario getUsuarioPorEmail(String email) throws RegraNegocioException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));
    }

    private String criptografarSenha(String senha){
        return new BCryptPasswordEncoder().encode(senha);
    }
}
