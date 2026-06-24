package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.ProfessorMapper;
import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.requestDtos.VinculoRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.entities.Professor;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.exceptions.UsuarioExistenteException;
import br.com.upe.espectrum.repositories.ProfessorRepository;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.CpfValidatorService;
import br.com.upe.espectrum.services.ProfessorService;
import br.com.upe.espectrum.services.UsuarioService;
import br.com.upe.espectrum.services.VinculoGeralService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final UsuarioService usuarioService;
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;
    private final VinculoGeralService  vinculoGeralService;
    private final CpfValidatorService cpfValidatorService;
    private final SecurityUtils securityUtils;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public ProfessorResponseDto cadastrarProfessor(ProfessorRequestDto dto) {

        Usuario usuarioLogado = securityUtils.getCurrentUser();

        if(usuarioLogado.getTipo() != Perfil.ROLE_TERAPEUTA){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Apenas Terapeutas podem cadastrar Professores.");
        }

        if (dto.email()==null||dto.email().isBlank()){
            throw new CampoObrigatorioException("Campo de Email é obrigatório");
        }
        if (dto.cpf()==null||dto.cpf().isBlank()){
            throw new CampoObrigatorioException("Campo de cpf é obrigatório");
        }
        if (dto.nome()==null||dto.nome().isBlank()){
            throw new CampoObrigatorioException("Campo de nome é obrigatório");
        }
        if (dto.numeroTelefone()==null||dto.numeroTelefone().isBlank()){
            throw new CampoObrigatorioException("Campo de número de telefone é obrigatório");
        }
        if (dto.senha()==null||dto.senha().isBlank()){
            throw new CampoObrigatorioException("Campo de senha é obrigatório");
        }
        if (professorRepository.findByUsuarioCpf(dto.cpf()).isPresent()){
            throw new UsuarioExistenteException("Já existe um professor com esse cpf");
        }
        if (professorRepository.findByUsuarioEmail(dto.email()).isPresent()){
            throw new UsuarioExistenteException("Já existe um professor com esse email");
        }

        String hash = passwordEncoder.encode(dto.senha());

        Usuario userbase = usuarioService.criarUsuario(
                dto.nome(),
                dto.numeroTelefone(),
                dto.email(),
                hash,
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
