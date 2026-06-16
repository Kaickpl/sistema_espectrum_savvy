package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponseDto {

    private UUID id;
    private String comentario;
    private LocalDateTime dataCriacao;
    private UUID autorId;
    private String autorNome;

    public ComentarioResponseDto(Comentario comentario) {
        this.id = comentario.getId();
        this.comentario = comentario.getComentario();
        this.dataCriacao = comentario.getDataCriacao();
        this.autorId = comentario.getUsuario().getId();
        this.autorNome = comentario.getUsuario().getNome();
    }


}
