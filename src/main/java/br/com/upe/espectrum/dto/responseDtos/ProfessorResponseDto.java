package br.com.upe.espectrum.dto.responseDtos;

import java.util.UUID;

public record ProfessorResponseDto(
        UUID idProfessor,
        String numeroTelefone,
        String email,
        String nome,
        String escola
) {
}
