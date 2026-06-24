package br.com.upe.espectrum.dto.responseDtos;

import java.util.UUID;

public record LoginResponseDto(
        String token,
        UUID idUsuario,
        String nome,
        String perfil
) {
}
