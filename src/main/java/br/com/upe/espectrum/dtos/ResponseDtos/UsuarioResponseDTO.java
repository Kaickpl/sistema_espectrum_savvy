package br.com.upe.espectrum.dtos.ResponseDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioResponseDTO (
        @JsonProperty(required = true)
        String numeroTelefone,
        @JsonProperty(required = true)
        String email,
        @JsonProperty(required = true)
        String cpf,
        @JsonProperty(required = true)
        String nome
){
}
