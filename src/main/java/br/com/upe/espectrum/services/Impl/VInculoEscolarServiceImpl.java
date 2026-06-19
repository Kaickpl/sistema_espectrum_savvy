package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.VinculoEscolarMapper;
import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResponseDto;
import br.com.upe.espectrum.entities.VinculoEscolar;
import br.com.upe.espectrum.repositories.VinculoEscolarRepository;
import br.com.upe.espectrum.services.VinculoEscolarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VInculoEscolarServiceImpl implements VinculoEscolarService {
    private final VinculoEscolarRepository vinculoEscolarRepository;
    private final VinculoEscolarMapper vinculoEscolarMapper;

    @Override
    public VinculoEscolarResponseDto criarVinculo(VinculoEscolar vinculoEscolar) {
        VinculoEscolar vinculo = vinculoEscolarRepository.save(vinculoEscolar);
        return vinculoEscolarMapper.entityToResponseDto(vinculoEscolar);
    }


}
