package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoTerapeutaResumoResponseDto(
        UUID id, UsuarioResponseDTO usuarioVinculado, LocalDate dataVinculo
) {
}
