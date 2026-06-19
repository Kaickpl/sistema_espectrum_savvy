package br.com.upe.espectrum.services;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.VinculoTerapeuta;

import java.util.UUID;

public interface VinculoTerapeutaService {
    public VinculoTerapeutaResponseDto buscarVinculo(UUID idVinculo);
    public VinculoTerapeutaResponseDto criarVinculo(VinculoTerapeuta vinculoTerapeuta);
}
