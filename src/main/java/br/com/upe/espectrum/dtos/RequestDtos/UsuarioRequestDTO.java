package br.com.upe.espectrum.dtos.RequestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UsuarioRequestDTO(
        @JsonProperty(required = true)
        String numeroTelefone,
        String email,
        @JsonProperty(required = true)
        String senha,
        @JsonProperty(required = true)
        String cpf,
        @JsonProperty(required = true)
        String nome
){}
