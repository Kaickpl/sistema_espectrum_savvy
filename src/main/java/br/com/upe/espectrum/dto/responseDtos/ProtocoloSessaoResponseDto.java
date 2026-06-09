package br.com.upe.espectrum.dto.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProtocoloSessaoResponseDto {
    private UUID usuarioId;
    private UUID pacienteId;

}
