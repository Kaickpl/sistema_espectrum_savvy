package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.dto.requestDtos.AtividadetempleteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTempleteResponseDto {

        private UUID idCategoriaTemplete;
        private String nomeCategoria;
        private List<AtividadetempleteDto> atividadeTemplete;

        public CategoriaTempleteResponseDto(CategoriaTemplete categoriaTemplete) {
            this.idCategoriaTemplete = categoriaTemplete.getId();
            this.nomeCategoria = categoriaTemplete.getNomeCategoria();
            this.atividadeTemplete = categoriaTemplete.getAtividades().stream().map(AtividadetempleteDto::new).collect(Collectors.toList());


        }

    }

