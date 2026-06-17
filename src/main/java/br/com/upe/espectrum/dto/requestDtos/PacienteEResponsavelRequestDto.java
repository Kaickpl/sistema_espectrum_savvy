package br.com.upe.espectrum.dto.requestDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PacienteEResponsavelRequestDto (
        //Responsavel
        @NotBlank(message = "O número de telefone é obrigatório")
        String numeroTelefone,
        @NotBlank(message = "O CPF do responsável é obrigatório")
        String cpfResponsavel,
        @NotBlank(message = "O nome do responsável é obrigatório")
        String nomeResponsavel,
        @Email(message = "Se preenchido, o formato deve ser validado")
        String emailResponsavel,
        String grauParentesco,

        // Paciente
        @Valid
        PacienteRequestDTO infosPaciente
){
}
