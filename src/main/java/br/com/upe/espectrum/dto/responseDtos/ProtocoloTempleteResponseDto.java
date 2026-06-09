package br.com.upe.espectrum.dto.responseDtos;
import br.com.upe.espectrum.dto.responseDtos.CategoriaTempleteResponseDto;
import br.com.upe.espectrum.dto.requestDtos.CategoriaTempleteDto;
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
    private List<CategoriaTempleteResponseDto> categoriaTempleteDto;

    public ProtocoloTempleteResponseDto(ProtocoloTemplete protocoloTemplete) {
        this.idTempleteProtocolo = protocoloTemplete.getId();
        this.categoriaTempleteDto =
                protocoloTemplete.getCategorias()
                        .stream()
                        .map(CategoriaTempleteResponseDto::new)
                        .toList();
    }


}
