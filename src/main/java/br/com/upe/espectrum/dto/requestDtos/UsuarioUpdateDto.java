package br.com.upe.espectrum.dto.requestDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateDto (
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "O número de telefone é obrigatório")
        String numeroTelefone
){
}
