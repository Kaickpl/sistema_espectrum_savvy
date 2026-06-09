package br.com.upe.espectrum.dto.responseDtos;
import br.com.upe.espectrum.entities.enums.Perfil;
import java.util.UUID;

public record AdminResponseDto(
        UUID idAdmin,
        UUID idUsuario,
        String nome,
        String numeroTelefone,
        String email,
        Perfil tipoPerfil
) {
}

