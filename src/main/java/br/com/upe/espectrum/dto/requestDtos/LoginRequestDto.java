package br.com.upe.espectrum.dto.requestDtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message =  "O login (e-mail ou telefone) é obrigatório.")
        String login,
        @NotBlank(message =  "A senha é obrigatória.")
        String senha
) {
}
