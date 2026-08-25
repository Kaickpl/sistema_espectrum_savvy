package br.com.upe.espectrum.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ObservacaoRelatorioDTO(
//        UUID id,
        String categoria,
        LocalDateTime data,
        String texto
){}
