package br.com.upe.espectrum.services;
import br.com.upe.espectrum.dto.responseDtos.PacienteResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteVinculadoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.VinculoTerapeuta;

import java.util.List;
import java.util.UUID;

public interface VinculoTerapeutaService {
    public VinculoTerapeutaResponseDto buscarVinculo(UUID idVinculo);
    public VinculoTerapeutaResponseDto criarVinculo(VinculoTerapeuta vinculoTerapeuta);
    public List<PacienteVinculadoResponseDto> listarPacientesVinculados(UUID idTerapeuta);
    public List<PacienteResumoResponseDto> listarPacientesDisponiveis(UUID idTerapeuta);
    public List<PacienteResumoResponseDto> listarMeusPacientesVinculados();
    public void desvincular(UUID idVinculo);
}
