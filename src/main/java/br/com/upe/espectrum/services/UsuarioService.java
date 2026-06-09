package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UsuarioService {
    public Usuario criarUsuario(
            String nome,
            String numeroTelefone,
            String email,
            String senha,
            String cpf,
            Perfil tipo,
            boolean isActive);

    public Usuario buscarUsuario(UUID id);
    public UsuarioResponseDTO editarPerfilUsuario(UUID id, UsuarioUpdateDto dto);
    public void desativarUsuario(UUID id);
    public void ativarUsuario(UUID id);
}

