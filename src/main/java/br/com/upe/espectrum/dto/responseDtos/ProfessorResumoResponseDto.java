package br.com.upe.espectrum.dto.responseDtos;

import java.util.UUID;

public record ProfessorResumoResponseDto(
        UUID id,
        String nome,
        String escola
) {
}
