package br.com.upe.espectrum.dtos.ResponseDtos;

import br.com.upe.espectrum.dtos.RequestDtos.CategoriaTempleteDto;
import br.com.upe.espectrum.entities.ProtocoloTemplete;
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
public class ProtocoloTempleteResponseDto {
    private UUID idTempleteProtocolo;
    private List<CategoriaTempleteDto> categoriaTempleteDto;

    public ProtocoloTempleteResponseDto(ProtocoloTemplete protocoloTemplete) {
        this.idTempleteProtocolo = protocoloTemplete.getId();
        this.categoriaTempleteDto = protocoloTemplete.getCategorias().stream().map(CategoriaTempleteDto::new).collect(Collectors.toList());

    }


}
