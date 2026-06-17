package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoTerapeutaResponseDto(
        UUID idVinculo,
        PacienteResponseDTO pacienteResponseDTO,
        TerapeutaResponseDto terapeutaResponseDto,
        LocalDate dataVinculo
) {
}
