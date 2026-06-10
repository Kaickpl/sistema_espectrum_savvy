package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteEResponsavelRequestDto (
        //Responsavel
        @JsonProperty(required = true)
        String numeroTelefone,
        @JsonProperty(required = true)
        String cpfResponsavel,
        @JsonProperty(required = true)
        String nomeResponsavel,
        String grauParentesco,

        // Paciente
        String nome,
        LocalDate dataNascimento,
        String genero,
        String cpf
){
}
