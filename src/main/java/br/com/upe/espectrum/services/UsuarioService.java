package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;

import java.util.UUID;

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
    public void verificarEmail(String email);
    public void desativarUsuario(UUID id);
    public void reativarUsuario(UUID id);
    public Usuario buscarUsuarioEntity(UUID id);
    void atualizarSenhaViaToken(UUID id, String novaSenha);
    public Usuario BuscarEmail(String email);
}

