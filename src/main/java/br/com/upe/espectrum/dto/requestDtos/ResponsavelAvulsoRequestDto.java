package br.com.upe.espectrum.dto.requestDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ResponsavelAvulsoRequestDto(
        @NotBlank
        String numeroTelefone,
        String email,
        @NotBlank
        String senha,
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        UUID idTutelado,
        String grauParentesco
){
}
