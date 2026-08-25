package br.com.upe.espectrum.dto.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioEvolucaoResponseDto {
    private UUID pacienteId;
    private String pacienteNome;
    private Integer idade;
    private Integer nivelSuporte;
    private String nomeTerapeuta;
    private List<String> categorias;
    private List<PontoEvolucaoResponseDto> evolucaoTemporal;
    private Map<String, Double> comparativoCategorias;
    private List<ObservacaoResponseDto> observacoes;
    private List<PontuacaoRecenteResponseDto> ultimasPontuacoes;
}
