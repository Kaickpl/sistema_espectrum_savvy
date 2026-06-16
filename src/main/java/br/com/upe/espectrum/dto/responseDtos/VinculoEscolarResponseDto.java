package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoEscolarResponseDto(
        UUID id, UsuarioResponseDTO usuarioVinculado, LocalDate dataVinculo) {
}
