package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.enums.GrauAutismo;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResumoResponseDto (
        UUID id,
        String nome,
        String genero,
        GrauAutismo grauAutismo,
        LocalDate dataNascimento
){
}
