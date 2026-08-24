package br.com.upe.espectrum.dto.responseDtos;

import java.time.LocalDateTime;

public record PontuacaoRecenteResponseDto(
        String nomeAtividade,
        String categoria,
        int numeroSessao,
        LocalDateTime data,
        double pontuacao
) {
}
