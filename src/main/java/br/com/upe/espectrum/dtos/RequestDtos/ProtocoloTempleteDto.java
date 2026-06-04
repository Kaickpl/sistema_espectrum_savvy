package br.com.upe.espectrum.dtos.RequestDtos;

import br.com.upe.espectrum.entities.ProtocoloTemplete;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocoloTempleteDto {
    private UUID idTempleteProtocolo;

    public ProtocoloTempleteDto(ProtocoloTemplete protocoloTemplete) {
        this.idTempleteProtocolo = protocoloTemplete.getId();
    }

}

