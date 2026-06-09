package br.com.upe.espectrum.dto.requestDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record ResponsavelAvulsoRequestDto(
        @JsonProperty(required = true)
        String numeroTelefone,
        String email,
        @JsonProperty(required = true)
        String senha,
        @JsonProperty(required = true)
        String cpf,
        @JsonProperty(required = true)
        String nome,
        @JsonProperty(required = true)
        UUID idTutelado,
        String grauParentesco
){
}
