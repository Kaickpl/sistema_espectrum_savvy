package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.enums.GrauAutismo;

import java.util.UUID;

public record PacienteVinculadoResponseDto(
        UUID idVinculo,
        UUID idPaciente,
        String nome,
        String genero,
        GrauAutismo grauAutismo
){
}
