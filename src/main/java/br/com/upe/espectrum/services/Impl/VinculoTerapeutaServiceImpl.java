package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.VinculoTerapeutaMapper;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import br.com.upe.espectrum.repositories.VinculoTerapeutaRepository;
import br.com.upe.espectrum.services.VinculoTerapeutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VinculoTerapeutaServiceImpl implements VinculoTerapeutaService {

    private final VinculoTerapeutaRepository vinculoTerapeutaRepository;
    private final VinculoTerapeutaMapper vinculoTerapeutaMapper;
    //mapper

    @Override
    public VinculoTerapeutaResponseDto buscarVinculo(UUID idVinculo) {
        VinculoTerapeuta vinculo = vinculoTerapeutaRepository.findById(idVinculo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vinculo não encontrado com o id "+idVinculo));

        return vinculoTerapeutaMapper.entityToResponseDto(vinculo);
  }
}
