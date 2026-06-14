package br.com.upe.espectrum.dto.requestDtos;

import br.com.upe.espectrum.entities.enums.Pontuacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarPontuacaoRequestDto {
    private Pontuacao  pontuacao;
}
