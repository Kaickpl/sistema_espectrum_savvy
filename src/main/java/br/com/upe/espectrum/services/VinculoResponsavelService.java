package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.responseDtos.VinculoResponsavelResponseDto;
import br.com.upe.espectrum.entities.VinculoResponsavel;

import java.util.UUID;

public interface VinculoResponsavelService {
    public VinculoResponsavelResponseDto buscarVinculoResponsavel(UUID id);

    public VinculoResponsavelResponseDto criarVinculoResponsavel(VinculoResponsavel vinculoResponsavel);
}