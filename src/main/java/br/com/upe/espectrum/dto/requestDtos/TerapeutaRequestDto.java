package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TerapeutaRequestDto(
        @JsonProperty(required = true)
        String numeroTelefone,
        @JsonProperty(required = true)
        String email,
        @JsonProperty(required = true)
        String senha,
        @JsonProperty(required = true)
        String cpf,
        @JsonProperty(required = true)
        String nome,
        String matricula,
        int periodo
) {
}
