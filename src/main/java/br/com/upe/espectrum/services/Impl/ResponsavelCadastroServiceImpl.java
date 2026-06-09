package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dto.mappers.ResponsavelMapper;
import br.com.upe.espectrum.dto.requestDtos.ResponsavelAvulsoRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Responsavel;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ResponsavelCadastroServiceImpl {
    private final UsuarioService usuarioService;
    private final ResponsavelRepository responsavelRepository;
    private final PacienteRepository pacienteRepository;
    private final ResponsavelMapper responsavelMapper;

    @Transactional
    public ResponsavelResponseDto cadastrarResponsavel(ResponsavelAvulsoRequestDto dto) {

        Usuario userBase = usuarioService.criarUsuario(
                dto.nome(),
                dto.numeroTelefone(),
                dto.email(),
                dto.senha(),
                dto.cpf(),
                Perfil.ROLE_RESPONSAVEL,
                true
        );

        Paciente pacienteVinculado = pacienteRepository.findById(dto.idTutelado()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Paciente com o id " + dto.idTutelado()+ " não encontrado.")
        );

        Responsavel responsavelNovo = responsavelMapper.responsavelAvulsoRequestDtoToEntity(dto, pacienteVinculado, userBase);
        responsavelRepository.save(responsavelNovo);
        return responsavelMapper.entityToResponseDto(responsavelNovo);
    }
}
