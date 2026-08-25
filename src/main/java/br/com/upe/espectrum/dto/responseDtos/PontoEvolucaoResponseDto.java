package br.com.upe.espectrum.dto.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PontoEvolucaoResponseDto {
    private LocalDateTime data;
    private double mediaGeral;
    private Map<String, Double> mediaPorCategoria;
}
