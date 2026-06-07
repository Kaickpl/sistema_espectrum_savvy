package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProfessorRequestDto (
        @JsonProperty(required = true)
        String numeroTelefone,
        @JsonProperty(required = true)
        String email,
        @JsonProperty(required = true)
        String senha,
        String cpf,
        String nome,
        String escola
){
}
