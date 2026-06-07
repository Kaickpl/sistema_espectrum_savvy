package br.com.upe.espectrum.dto.responseDtos;
import br.com.upe.espectrum.dto.requestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeTempleteResponseDto {
     private String nomeAtividade;

     public AtividadeTempleteResponseDto (AtividadeTemplete atividadeTemplete) {
         this.nomeAtividade = atividadeTemplete.getNomeAtividade();
     }




}
