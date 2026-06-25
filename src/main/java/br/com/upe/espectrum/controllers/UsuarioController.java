package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.mappers.UsuarioMapper;
import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.PerfilAtualizadoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.security.TokenConfig;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final SecurityUtils securityUtils;
    private final TokenConfig tokenConfig;

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> buscarPerfil() {
        UUID idUsuarioLogado = securityUtils.getCurrentUserId();
        UsuarioResponseDTO response = usuarioMapper.entityToResponseDto(usuarioService.buscarUsuarioEntity(idUsuarioLogado));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/perfil")
    public ResponseEntity<PerfilAtualizadoResponseDto> editarPerfil(@Valid @RequestBody UsuarioUpdateDto dto) {
        UUID idUsuarioLogado = securityUtils.getCurrentUserId();
        UsuarioResponseDTO response = usuarioService.editarPerfilUsuario(idUsuarioLogado, dto);

        Usuario usuarioAtualizado = usuarioService.buscarUsuarioEntity(idUsuarioLogado);
        String novoToken = tokenConfig.generateToken(usuarioAtualizado);

        return ResponseEntity.ok(new PerfilAtualizadoResponseDto(response, novoToken));
    }

    @PatchMapping("/desativar")
    public ResponseEntity<UsuarioResponseDTO> desativarPerfil() {
        UUID idUsuarioLogado = securityUtils.getCurrentUserId();
        usuarioService.desativarUsuario(idUsuarioLogado);
        UsuarioResponseDTO response = usuarioMapper.entityToResponseDto(usuarioService.buscarUsuarioEntity(idUsuarioLogado));
        return ResponseEntity.ok(response);
    }
}

