package br.com.upe.espectrum.dto.responseDtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record UsuarioResponseDTO (
        @JsonProperty(required = true)
        UUID id,
        @JsonProperty(required = true)
        String numeroTelefone,
        @JsonProperty(required = true)
        String email,
        @JsonProperty(required = true)
        String nome,

        boolean isActive
){
}
