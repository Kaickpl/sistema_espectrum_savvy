package br.com.upe.espectrum.entities.DTOs;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTempleteDto {
    private String nomeCategoria;
    private String descricaoCategoria;

    public CategoriaTempleteDto(CategoriaTemplete categoriaTemplete) {
        this.nomeCategoria = categoriaTemplete.getNomeCategoria();
        this.descricaoCategoria = categoriaTemplete.getDescricaoCategoria();

    }

}
