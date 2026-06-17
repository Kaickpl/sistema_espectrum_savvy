package br.com.upe.espectrum.dto.mappers;

import br.com.upe.espectrum.dto.responseDtos.VinculoResponsavelResponseDto;
import br.com.upe.espectrum.entities.VinculoResponsavel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class VinculoResponsavelMapper {

    private final PacienteMapper pacienteMapper;
    private final UsuarioMapper usuarioMapper;

    public VinculoResponsavelResponseDto entityToResponseDto(VinculoResponsavel vinculoResponsavel){
        VinculoResponsavelResponseDto response = new VinculoResponsavelResponseDto(
                vinculoResponsavel.getId(),
                pacienteMapper.entityToResponseDto(vinculoResponsavel.getPaciente()),
                usuarioMapper.entityToResponseDto(vinculoResponsavel.getUsuario()),
                vinculoResponsavel.getDataVinculo(),
                vinculoResponsavel.getGrauParentesco()
        );

        return response;
    }
}
