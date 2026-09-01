package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.Comentario;

import java.time.LocalDateTime;
import java.util.UUID;

public record ObservacaoResponseDto(
        UUID id,
        String comentario,
        LocalDateTime dataCriacao,
        String autorNome,
        String categoria,
        Integer numeroAplicacao
) {
    public ObservacaoResponseDto(Comentario comentario, String categoria, Integer numeroAplicacao) {
        this(
                comentario.getId(),
                comentario.getComentario(),
                comentario.getDataCriacao(),
                comentario.getUsuario() != null ? comentario.getUsuario().getNome() : null,
                categoria,
                numeroAplicacao
        );
    }
}
