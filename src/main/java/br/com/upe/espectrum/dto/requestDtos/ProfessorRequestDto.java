package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDto (
        @NotBlank
        String numeroTelefone,
        @NotBlank
        String email,
        @NotBlank
        String senha,
        String cpf,
        String nome,
        String escola
){
}
