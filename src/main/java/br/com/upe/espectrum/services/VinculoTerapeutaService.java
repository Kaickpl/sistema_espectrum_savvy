package br.com.upe.espectrum.services;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import java.util.UUID;

public interface VinculoTerapeutaService {
    public VinculoTerapeutaResponseDto buscarVinculo(UUID idVinculo);

}
