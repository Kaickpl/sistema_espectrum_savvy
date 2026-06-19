package br.com.upe.espectrum.dto.responseDtos;

import java.util.UUID;

public record VinculoGeralResumoResponseDto(
        UUID idPaciente,
        UUID idUsuario
) {
}
