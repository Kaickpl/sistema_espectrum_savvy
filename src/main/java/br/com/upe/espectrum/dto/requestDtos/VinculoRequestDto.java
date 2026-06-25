package br.com.upe.espectrum.dto.requestDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public record VinculoRequestDto(
        @NotNull(message = "O id do paciente é obrigatório.")
        UUID idPaciente,
        UUID idUsuario,
        String grauParentesco,
        @Pattern(regexp = "^\\d{4}$", message = "O ano letivo deve conter exatamente 4 números (ex: 2024)")
        String anoLetivo
) {
}
