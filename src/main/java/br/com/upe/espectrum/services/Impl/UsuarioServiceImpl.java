package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dtos.RequestDtos.UsuarioRequestDTO;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

public class UsuarioServiceImpl implements UsuarioService {
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario buscarUsuario(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuário não encontrado com o id: " + id));
    }

    @Override
    public Usuario cadastrarUsuario(UUID id, UsuarioRequestDTO dto) {
        return null;
    }

    @Override
    public Usuario editarUsuario(UUID id, UsuarioRequestDTO dto) {
        return null;
    }

    @Override
    public void desativarUsuario(UUID id) {

    }

    @Override
    public void ativarUsuario(UUID id) {

    }
}
