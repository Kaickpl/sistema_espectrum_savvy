package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoResponsavelResponseDto(
        UUID id,
        PacienteResponseDTO usuarioVinculado,
        UsuarioResponseDTO usuarioResponseDTO,
        LocalDate dataVinculo,
        String grauParentesco
) {
}
