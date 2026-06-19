package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoResponsavelResumoResponseDto(
        UUID id, UsuarioResponseDTO usuarioVinculado, String grauParentesco ,LocalDate dataVinculo
) {
}
