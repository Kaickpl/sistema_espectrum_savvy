package br.com.upe.espectrum.dto.responseDtos;
import br.com.upe.espectrum.entities.enums.StatusCadastro;

import java.util.UUID;

public record TerapeutaResponseDto(
        UUID id,
        String numeroTelefone,
        String email,
        String nome,
        String matricula,
        int periodo,
        String statusCadastro,
        boolean ativo
) {
}
