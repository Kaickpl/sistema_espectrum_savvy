package br.com.upe.espectrum.dto.requestDtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record EnderecoRequestDTO (

        @NotBlank(message = "O CEP é obrigatório")
        String cep,

        @NotBlank(message = "A rua é obrigatória")
        String rua,

        @NotBlank(message = "O número é obrigatório")
        String numero,

        String complemento,

        @NotBlank(message = "O bairro é obrigatório")
        String bairro,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        String estado

) {
}

