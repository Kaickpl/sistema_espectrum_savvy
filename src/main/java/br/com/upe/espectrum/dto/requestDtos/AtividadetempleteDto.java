package br.com.upe.espectrum.dto.requestDtos;

import br.com.upe.espectrum.entities.AtividadeTemplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtividadetempleteDto {
    private String nomeAtividade;
    public AtividadetempleteDto(AtividadeTemplete atividadeTemplete){
        this.nomeAtividade = atividadeTemplete.getNomeAtividade();
    }
}
