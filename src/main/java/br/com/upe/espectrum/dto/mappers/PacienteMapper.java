package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.EnderecoRequestDTO;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.EnderecoResponseDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.dto.responseDtos.VinculoEscolarResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResumoResponseDto;
import br.com.upe.espectrum.entities.Endereco;
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
        paciente.setEndereco(enderecoRequestDtoToEntity(dto.endereco()));

        return paciente;
    }

    public Endereco enderecoRequestDtoToEntity(EnderecoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());

        return endereco;
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
                paciente.getId(),
                paciente.getNome(),
                paciente.getDataNascimento(),
                paciente.getGenero(),
                paciente.getCpf(),
                paciente.getAdmin().getId(),
                paciente.getGrauAutismo(),
                enderecoToResponseDto(paciente.getEndereco()),
                vinculosTerapeutasDto,
                vinculoEscolarResponseDtos
        );
    }

    public EnderecoResponseDTO enderecoToResponseDto(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        return new EnderecoResponseDTO(
                endereco.getCep(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado()
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
                usuarioMapper.entityToResponseDto(vinculo.getUsuario()),
                vinculo.getUsuario().getProfessor().getEscola()
        );
    }

}
