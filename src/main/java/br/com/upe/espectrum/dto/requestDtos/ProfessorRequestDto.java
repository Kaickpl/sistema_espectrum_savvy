package br.com.upe.espectrum.dto.requestDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDto (
        @NotBlank(message = "O número de telefone é obrigatório")
        String numeroTelefone,
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        @NotBlank(message = "O nome do professor é obrigatório")
        String nome,

        @NotBlank(message = "O nome da escola é obrigatório")
        String escola
){
}
