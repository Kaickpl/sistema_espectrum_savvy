package br.com.upe.espectrum.dto.responseDtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoEscolarResponseDto(
        UUID id, PacienteResponseDTO usuarioVinculado,
        UsuarioResponseDTO usuarioResponseDTO, LocalDate dataVinculo, String escola
) {
}

