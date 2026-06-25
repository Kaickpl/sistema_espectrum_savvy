package br.com.upe.espectrum.dto.requestDtos;

import br.com.upe.espectrum.entities.enums.CategoriaSuporte;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuporteRequestDto {
    private Set<CategoriaSuporte> categorias;
    private String descricao;
    private String email;
}
