package br.com.upe.espectrum.dto.requestDtos;

import br.com.upe.espectrum.entities.enums.GrauAutismo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteRequestDTO(

        @NotBlank(message = "O nome do paciente é obrigatório")
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @NotBlank(message = "O gênero é obrigatório")
        String genero,

        String cpf, // CPF do paciente pode ser nulo se ele for criança? Se não, coloque @NotBlank!

        @NotNull(message = "O grau de autismo é obrigatório")
        GrauAutismo grauAutismo,

        @Valid
        @NotNull(message = "O endereço é obrigatório")
                EnderecoRequestDTO endereco
) {
}