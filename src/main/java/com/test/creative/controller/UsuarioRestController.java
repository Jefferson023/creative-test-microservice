package com.test.creative.controller;

import com.test.creative.domain.Usuario;
import com.test.creative.dto.RequestUsuarioDto;
import com.test.creative.dto.ResponseUsuarioDto;
import com.test.creative.exception.RegraNegocioException;
import com.test.creative.mapper.ResponseUsuarioDtoMapper;
import com.test.creative.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUsuarioDto createUsuario(@Valid @RequestBody RequestUsuarioDto usuarioDto) {
        try {
            final var usuario = this.usuarioService.criarUsuario(usuarioDto);
            return ResponseUsuarioDtoMapper.map(usuario);
        } catch (RegraNegocioException regraNegocioException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, regraNegocioException.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUsuario(@PathVariable String usuarioId) {
        try {
            usuarioService.removerUsuario(usuarioId);
        } catch (RegraNegocioException regraNegocioException) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, regraNegocioException.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseUsuarioDto updateUsuario(@Valid @RequestBody RequestUsuarioDto usuarioDto) {
        try {
            final var usuario = usuarioService.atualizarUsuario(usuarioDto);
            return ResponseUsuarioDtoMapper.map(usuario);
        } catch (RegraNegocioException regraNegocioException) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, regraNegocioException.getMessage());
        }
    }

    @GetMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public ResponseUsuarioDto getUsuario(@PathVariable String usuarioId) {
        final Usuario usuario;
        try {
            usuario = usuarioService.getUsuario(usuarioId);
            return ResponseUsuarioDtoMapper.map(usuario);
        } catch (RegraNegocioException regraNegocioException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, regraNegocioException.getMessage());
        }
    }

    @GetMapping("/buscar")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public Page<ResponseUsuarioDto> buscarUsuario(
            @RequestParam(required = false, defaultValue = "") String nome,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String telefone,
            @RequestParam(required = false, defaultValue = "") String endereco,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") Collection<String> sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order) {

        final var buscaUsuarios = this.usuarioService.buscaUsuarioComFiltro(nome, email, telefone, endereco, page, size, sortBy, order);
        return buscaUsuarios.map(ResponseUsuarioDtoMapper::map);
    }
}