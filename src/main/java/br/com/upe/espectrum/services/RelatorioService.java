package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.RelatorioEvolucaoDTO;
import br.com.upe.espectrum.dto.responseDtos.RelatorioEvolucaoResponseDto;

import java.util.UUID;

public interface RelatorioService {
    RelatorioEvolucaoResponseDto gerarRelatorioEvolucao(UUID pacienteId, int meses);
}
