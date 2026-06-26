package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResponseDto;
import br.com.upe.espectrum.entities.VinculoEscolar;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface VinculoEscolarService {
    public VinculoEscolarResponseDto criarVinculo(VinculoEscolar vinculoEscolar);
    public void desvincular(UUID idVinculo);
}
