package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.enums.Pontuacao;
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
public class AtividadeSessaoResponseDto {
    private UUID id;
    private String nomeAtividade;
    private Pontuacao pontuacao;
    private int valorPontuacao;
    private LocalDateTime atualizadoEm;
    private String atualizadoPorNome;

    public AtividadeSessaoResponseDto(AtividadeSessao atividadeSessao) {
        this.id = atividadeSessao.getId();
        this.nomeAtividade = atividadeSessao.getAtividadeTemplete().getNomeAtividade();
        this.pontuacao = atividadeSessao.getPontuacao();
        this.valorPontuacao = atividadeSessao.getPontuacao() != null
                ? atividadeSessao.getPontuacao().getValor()
                : 0;
        this.atualizadoEm = atividadeSessao.getAtualizadoEm();
        this.atualizadoPorNome = atividadeSessao.getAtualizadoPor() != null
                ? atividadeSessao.getAtualizadoPor().getNome()
                : null;
    }
}
