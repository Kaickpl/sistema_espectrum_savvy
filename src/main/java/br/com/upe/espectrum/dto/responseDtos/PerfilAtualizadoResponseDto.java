package br.com.upe.espectrum.dto.responseDtos;

public record PerfilAtualizadoResponseDto(
        UsuarioResponseDTO usuario,
        String token
) {
}
