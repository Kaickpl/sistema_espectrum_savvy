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
public class ComentarioResponseCategoriaDto {


        private UUID id;
        private String comentario;
        private LocalDateTime dataCriacao;
        private UUID autorId;
        private String autorNome;
        private String NomeCategoria;

        public ComentarioResponseCategoriaDto(Comentario comentario) {
            this.id = comentario.getId();
            this.comentario = comentario.getComentario();
            this.dataCriacao = comentario.getDataCriacao();
            this.autorId = comentario.getUsuario().getId();
            this.autorNome = comentario.getUsuario().getNome();
            this.NomeCategoria = comentario.getCategoriaSessao().getCategoriaTemplete().getNomeCategoria();
        }


    }
