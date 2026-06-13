package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record TerapeutaRequestDto(
        @NotBlank
        String numeroTelefone,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        String matricula,
        int periodo
) {
}
