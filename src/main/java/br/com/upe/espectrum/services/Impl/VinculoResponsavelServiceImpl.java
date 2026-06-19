package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.VinculoResponsavelMapper;
import br.com.upe.espectrum.dto.responseDtos.VinculoResponsavelResponseDto;
import br.com.upe.espectrum.entities.VinculoEscolar;
import br.com.upe.espectrum.entities.VinculoResponsavel;
import br.com.upe.espectrum.repositories.VinculoResponsavelRepository;
import br.com.upe.espectrum.services.VinculoResponsavelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VinculoResponsavelServiceImpl implements VinculoResponsavelService {
    private final VinculoResponsavelRepository vinculoResponsavelRepository;
    private final VinculoResponsavelMapper vinculoResponsavelMapper;

    @Override
    public VinculoResponsavelResponseDto buscarVinculoResponsavel(UUID id) {
        VinculoResponsavel vinculoResponsavel = vinculoResponsavelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Vnculo de responsável não encontrado com o id+ "));

        return vinculoResponsavelMapper.entityToResponseDto(vinculoResponsavel);
    }

    @Override
    public VinculoResponsavelResponseDto criarVinculoResponsavel(VinculoResponsavel vinculoResponsavel) {
        VinculoResponsavel vinculoEntity = vinculoResponsavelRepository.save(vinculoResponsavel);
        return vinculoResponsavelMapper.entityToResponseDto(vinculoEntity);
    }


}
