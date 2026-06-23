package br.com.upe.espectrum.services.Impl.Protocolo;
import br.com.upe.espectrum.dto.mappers.PacienteMapper;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.UsuarioService;
import br.com.upe.espectrum.services.paciente.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository pacienteRepository;
    private final AdminRepository adminRepository;
    private final ResponsavelRepository responsavelRepository;
    private final TerapeutaRepository terapeutaRepository;
    private final PacienteMapper pacienteMapper;
    private final UsuarioService usuarioService;
    private final SecurityUtils securityUtils;


    // APAGUEI PQ O CADASTRO DE PACIENTE EH JUNTO COM RESPONSAVEL
//    @Override
//    @Transactional
//    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO) {
//        Paciente paciente = new Paciente();
//        paciente.setNome(pacienteRequestDTO.nome());
//        paciente.setDataNascimento(pacienteRequestDTO.dataNascimento());
//        paciente.setGenero(pacienteRequestDTO.genero());
//        paciente.setCpf(pacienteRequestDTO.cpf());
//        Admin admin = adminRepository.findById(pacienteRequestDTO.adminId())
//                .orElseThrow(() -> new RuntimeException("Admin não encontrado no banco de dados"));
//        paciente.setAdmin(admin);
//
//        return pacienteRepository.save(paciente);
//    }

    @Override
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId) {
        List<Paciente> pacienteList = pacienteRepository.findByAdminId(adminId);
        return pacienteList.stream()
                .map(pacienteMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponseDTO mostrarPaciente(UUID pacienteID) {
        Paciente paciente = pacienteRepository.findById(pacienteID)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado no banco de dados!"));
        return pacienteMapper.entityToResponseDto(paciente);
    }

    @Override
    @Transactional
    public List<PacienteResponseDTO> listarPacientesDoUsuario(){

//
//        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Usuario usuario = usuarioService.buscarUsuarioEntity(usuarioLogado.getId());

        Usuario usuario = securityUtils.getCurrentUser();

        List<Paciente> pacientesLigados = new ArrayList<>();

        switch (usuario.getTipo()){

            case ROLE_ADMIN -> {
                pacientesLigados = pacienteRepository.findByAdminId(usuario.getPerfilAdmin().getId());
            }

            case ROLE_TERAPEUTA -> {
                pacientesLigados = usuario.getVinculoTerapeutas()
                        .stream()
                        .map(VinculoTerapeuta::getPaciente)
                        .toList();
            }

            case ROLE_PROFESSOR -> {
                pacientesLigados = usuario.getVinculosEscolares()
                        .stream().map(VinculoEscolar::getPaciente)
                        .toList();
            }

            case ROLE_RESPONSAVEL -> {
                pacientesLigados = usuario.getVinculosResponsaveis()
                        .stream()
                        .map(VinculoResponsavel::getPaciente)
                        .toList();
            }

            default -> throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Perfil não autorizado a listar pacientes");
        }

        return pacientesLigados.stream()
                .map(pacienteMapper::entityToResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void desativarContaPaciente(UUID pacienteId){
        Boolean isAtivo = pacienteRepository.verificarStatusConta(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado com o id "+pacienteId));

        if(!isAtivo){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O paciente já está com a conta desativada");
        }

        pacienteRepository.alterarStatusDiretoNoBanco(pacienteId, false);
    }

    @Override
    @Transactional
    public void reativarContaPaciente(UUID pacienteId){
        Boolean isAtivo = pacienteRepository.verificarStatusConta(pacienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado com o id "+pacienteId));

        if(isAtivo){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O paciente já está ativo.");
        }

        pacienteRepository.alterarStatusDiretoNoBanco(pacienteId, true);
    }

    public Paciente mostrarPacienteEntity(UUID pacienteID) {
        Paciente paciente = pacienteRepository.findById(pacienteID)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado no banco de dados!"));
        return paciente;
    }

}
