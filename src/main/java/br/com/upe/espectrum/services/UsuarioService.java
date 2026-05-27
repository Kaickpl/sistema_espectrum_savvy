package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dtos.RequestDtos.UsuarioRequestDTO;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UsuarioService {
    public Usuario buscarUsuario(UUID id);
    public Usuario cadastrarUsuario(UUID id, UsuarioRequestDTO dto);
    public Usuario editarUsuario(UUID id, UsuarioRequestDTO dto);
    public void desativarUsuario(UUID id);
    public void ativarUsuario(UUID id);
}