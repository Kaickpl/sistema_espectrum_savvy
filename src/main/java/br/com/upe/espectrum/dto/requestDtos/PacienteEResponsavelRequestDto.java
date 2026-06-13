package br.com.upe.espectrum.dto.requestDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PacienteEResponsavelRequestDto (
        //Responsavel
        @NotBlank
        String numeroTelefone,
        @NotBlank
        String cpfResponsavel,
        @NotBlank
        String nomeResponsavel,
        String grauParentesco,

        // Paciente
        @Valid
        PacienteRequestDTO infosPaciente
){
}
