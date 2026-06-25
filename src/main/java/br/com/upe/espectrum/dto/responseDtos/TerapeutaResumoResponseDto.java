package br.com.upe.espectrum.dto.responseDtos;
import br.com.upe.espectrum.entities.enums.StatusCadastro;

import java.util.UUID;

public record TerapeutaResumoResponseDto(
        UUID id,
        String nome,
        StatusCadastro statusCadastro,
        long quantidadePacientes
) {
}
