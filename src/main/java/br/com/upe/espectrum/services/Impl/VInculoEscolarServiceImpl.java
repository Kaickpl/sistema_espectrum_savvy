package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.VinculoEscolarMapper;
import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResponseDto;
import br.com.upe.espectrum.entities.VinculoEscolar;
import br.com.upe.espectrum.repositories.VinculoEscolarRepository;
import br.com.upe.espectrum.services.VinculoEscolarService;
import br.com.upe.espectrum.services.paciente.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VInculoEscolarServiceImpl implements VinculoEscolarService {
    private final VinculoEscolarRepository vinculoEscolarRepository;
    private final VinculoEscolarMapper vinculoEscolarMapper;
    private final PacienteService pacienteService;

    @Override
    public VinculoEscolarResponseDto criarVinculo(VinculoEscolar vinculoEscolar) {
        boolean jaVinculado = vinculoEscolarRepository.existsByPacienteIdAndUsuarioId(
                vinculoEscolar.getPaciente().getId(), vinculoEscolar.getUsuario().getId());

        if (jaVinculado) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este paciente já está vinculado a este professor.");
        }

        VinculoEscolar vinculo = vinculoEscolarRepository.save(vinculoEscolar);
        return vinculoEscolarMapper.entityToResponseDto(vinculo);
    }

    @Override
    @Transactional
    public void desvincular(UUID idVinculo) {
        VinculoEscolar vinculo = vinculoEscolarRepository.findById(idVinculo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vinculo não encontrado com o id " + idVinculo));

        if (!pacienteService.verificarSePacientePertenceAoUsuario(vinculo.getPaciente().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Você não tem permissão para interagir com este paciente.");
        }

        vinculoEscolarRepository.deleteById(idVinculo);
    }

}
