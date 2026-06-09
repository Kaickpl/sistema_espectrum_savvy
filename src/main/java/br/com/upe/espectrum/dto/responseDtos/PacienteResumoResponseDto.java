package br.com.upe.espectrum.dto.responseDtos;

import java.util.UUID;

public record PacienteResumoResponseDto (
        UUID id,
        String nome
){
}
