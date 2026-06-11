package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.CategoriaSessao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaSessaoResponseDto {
    private UUID id;
    private String nomeCategoria;
    private String descricaoCategoria;
    private List<AtividadeSessaoResponseDto> atividades;
    public CategoriaSessaoResponseDto(CategoriaSessao categoriaSessao) {
        this.id = categoriaSessao.getId();
        this.nomeCategoria = categoriaSessao.getCategoriaTemplete().getNomeCategoria();
        this.descricaoCategoria = categoriaSessao.getCategoriaTemplete().getDescricaoCategoria();
        this.atividades = categoriaSessao.getAtividadeSessao()
                .stream()
                .map(AtividadeSessaoResponseDto::new)
                .toList();
    }
}
