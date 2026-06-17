package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResumoResponseDto;
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

        List<VinculoTerapeutaResumoResponseDto> vinculosTerapeutasDto =
                paciente.getEquipeMultiDisciplinar() != null
                        ? paciente.getEquipeMultiDisciplinar().stream()
                        .map(this::vinculoTerapeutaToResumoResponseDto)
                        .toList()
                        : Collections.emptyList();

        List<VinculoEscolarResumoResponseDto> vinculoEscolarResponseDtos =
                paciente.getVinculosEscolar() != null
                        ? paciente.getVinculosEscolar().stream()
                        .map(this::vinculoEscolarToResumoResponseDto)
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

    public VinculoTerapeutaResumoResponseDto vinculoTerapeutaToResumoResponseDto(VinculoTerapeuta vinculo) {
        if (vinculo == null) {
            return null;
        }

        return new VinculoTerapeutaResumoResponseDto(
                vinculo.getId(),
                usuarioMapper.entityToResponseDto(vinculo.getUsuario()),
                vinculo.getDataVinculo()
        );
    }

    public VinculoEscolarResumoResponseDto vinculoEscolarToResumoResponseDto(VinculoEscolar vinculo) {
        if (vinculo == null) {
            return null;
        }

        return new VinculoEscolarResumoResponseDto(
                vinculo.getId(),
                usuarioMapper.entityToResponseDto(vinculo.getProfessor().getUsuario()),
                vinculo.getProfessor().getEscola()
        );
    }

}