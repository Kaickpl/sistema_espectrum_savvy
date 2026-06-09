package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.ProfessorMapper;
import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.entities.Professor;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.ProfessorRepository;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorCadastroServiceImpl {
    private final UsuarioService usuarioService;
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    @Transactional
    public ProfessorResponseDto cadastrarProfessor(ProfessorRequestDto dto) {

        Usuario userbase = usuarioService.criarUsuario(
                dto.nome(),
                dto.numeroTelefone(),
                dto.email(),
                dto.senha(),
                dto.cpf(),
                Perfil.ROLE_PROFESSOR,
                true
        );

        Professor professorNovo = professorMapper.requestDtoToEntity(dto);
        professorNovo.setUsuario(userbase);
        professorNovo.setEscola(dto.escola());
        professorRepository.save(professorNovo);
        return professorMapper.entityToResponseDto(professorNovo);
    }
}
