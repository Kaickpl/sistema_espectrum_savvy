package br.com.upe.espectrum.dto.mappers;

import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResponseDto;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.VinculoEscolar;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PacienteMapper {

    private final UsuarioMapper usuarioMapper;

    public Paciente requestDtoToEntity(PacienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setGenero(dto.genero());
        paciente.setCpf(dto.cpf());
        paciente.setGrauAutismo(dto.grauAutismo());

        return paciente;
    }

    public PacienteResponseDTO entityToResponseDto(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        List<VinculoTerapeutaResponseDto> vinculosTerapeutasDto =
                paciente.getEquipeMultiDisciplinar() != null
                        ? paciente.getEquipeMultiDisciplinar().stream()
                        .map(this::vinculoToResponseDto)
                        .toList()
                        : Collections.emptyList();

        List<VinculoEscolarResponseDto> vinculoEscolarResponseDtos =
                paciente.getVinculosEscolar() != null
                        ? paciente.getVinculosEscolar().stream()
                        .map(this::vinculoEscolarToResponseDto)
                        .toList()
                        : Collections.emptyList();

        return new PacienteResponseDTO(
                paciente.getNome(),
                paciente.getDataNascimento(),
                paciente.getGenero(),
                paciente.getCpf(),
                paciente.getAdmin().getId(),
                paciente.getGrauAutismo(),
                vinculosTerapeutasDto,
                vinculoEscolarResponseDtos
        );
    }

    public VinculoTerapeutaResponseDto vinculoToResponseDto(VinculoTerapeuta vinculo) {
        if (vinculo == null) {
            return null;
        }

        return new VinculoTerapeutaResponseDto(
                vinculo.getId(),
                usuarioMapper.entityToResponseDto(vinculo.getUsuario()),
                vinculo.getDataVinculo()
        );
    }

    public VinculoEscolarResponseDto vinculoEscolarToResponseDto(VinculoEscolar vinculoEscolar){
        if (vinculoEscolar == null){
            return null;
        }

        return new VinculoEscolarResponseDto(
                vinculoEscolar.getId(),
                usuarioMapper.entityToResponseDto(vinculoEscolar.getProfessor().getUsuario()),
                vinculoEscolar.getDataInicio()
        );
    }

}