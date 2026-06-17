package br.com.upe.espectrum.dto.mappers;

import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResponseDto;
import br.com.upe.espectrum.entities.VinculoEscolar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VinculoEscolarMapper {
    private final PacienteMapper pacienteMapper;
    private final UsuarioMapper usuarioMapper;

    public VinculoEscolarResponseDto entityToResponseDto(VinculoEscolar vinculoEscolar) {

        VinculoEscolarResponseDto dto = new VinculoEscolarResponseDto(
                vinculoEscolar.getId(),
                pacienteMapper.entityToResponseDto(vinculoEscolar.getPaciente()),
                usuarioMapper.entityToResponseDto(vinculoEscolar.getProfessor().getUsuario()),
                vinculoEscolar.getDataInicio(),
                vinculoEscolar.getProfessor().getEscola());

        return dto;
    }
}
