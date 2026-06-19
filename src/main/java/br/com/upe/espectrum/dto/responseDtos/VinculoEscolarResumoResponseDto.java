package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDate;
import java.util.UUID;

public record VinculoEscolarResumoResponseDto (
        UUID id, UsuarioResponseDTO usuarioVinculado, String escola){

}
