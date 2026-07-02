package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.PacienteMapper;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.UsuarioService;
import br.com.upe.espectrum.services.paciente.PacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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

        UUID userId = securityUtils.getCurrentUserId();
        Usuario usuario = usuarioService.buscarUsuarioEntity(userId);

        List<Paciente> pacientesLigados = new ArrayList<>();

        switch (usuario.getTipo()){

            case ROLE_SUPERVISOR_ESTAGIO -> {
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
    public PacienteResponseDTO editarPaciente(UUID pacienteId, PacienteRequestDTO dto) {
        Usuario usuarioLogado = usuarioService.buscarUsuarioEntity(securityUtils.getCurrentUserId());

        if (usuarioLogado.getTipo() != Perfil.ROLE_TERAPEUTA && usuarioLogado.getTipo() != Perfil.ROLE_SUPERVISOR_ESTAGIO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas terapeutas ou supervisores de estágio podem editar pacientes");
        }

        if (!verificarSePacientePertenceAoUsuario(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar este paciente");
        }

        Paciente paciente = mostrarPacienteEntity(pacienteId);
        paciente.setNome(dto.nome());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setGenero(dto.genero());
        paciente.setCpf(dto.cpf());
        paciente.setGrauAutismo(dto.grauAutismo());

        Endereco endereco = paciente.getEndereco();
        if (endereco == null) {
            paciente.setEndereco(pacienteMapper.enderecoRequestDtoToEntity(dto.endereco()));
        } else {
            endereco.setCep(dto.endereco().cep());
            endereco.setRua(dto.endereco().rua());
            endereco.setNumero(dto.endereco().numero());
            endereco.setComplemento(dto.endereco().complemento());
            endereco.setBairro(dto.endereco().bairro());
            endereco.setCidade(dto.endereco().cidade());
            endereco.setEstado(dto.endereco().estado());
        }

        Paciente pacienteAtualizado = pacienteRepository.save(paciente);
        return pacienteMapper.entityToResponseDto(pacienteAtualizado);
    }

    @Override
    @Transactional
    public void desativarContaPaciente(UUID pacienteId){
        Usuario usuarioLogado = usuarioService.buscarUsuarioEntity(securityUtils.getCurrentUserId());

        if (usuarioLogado.getTipo() != Perfil.ROLE_TERAPEUTA && usuarioLogado.getTipo() != Perfil.ROLE_SUPERVISOR_ESTAGIO) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas terapeutas ou supervisores de estágio podem excluir pacientes");
        }

        if (!verificarSePacientePertenceAoUsuario(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir este paciente");
        }

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

    @Override
    @Transactional
    public boolean verificarSePacientePertenceAoUsuario(UUID idPaciente){
        Usuario usuarioLogado = usuarioService.buscarUsuarioEntity(securityUtils.getCurrentUserId());
        UUID usuarioId = securityUtils.getCurrentUserId();

        log.info("verificarSePacientePertenceAoUsuario: usuarioId={} tipo={} idPaciente={} vinculosTerapeuta={}",
                usuarioId, usuarioLogado.getTipo(), idPaciente,
                usuarioLogado.getVinculoTerapeutas().stream()
                        .map(v -> v.getPaciente().getId())
                        .toList());

        switch (usuarioLogado.getTipo()){
            case ROLE_SUPERVISOR_ESTAGIO -> {
                return pacienteRepository.findById(idPaciente)
                        .map(paciente -> paciente.getAdmin().getId()
                                .equals(usuarioLogado.getPerfilAdmin().getId())).orElse(false);
            }

            case ROLE_PROFESSOR -> {
                return usuarioLogado.getVinculosEscolares()
                        .stream().anyMatch(v -> v.getPaciente().getId().equals(idPaciente));
            }

            case ROLE_TERAPEUTA -> {
                return usuarioLogado.getVinculoTerapeutas()
                        .stream().anyMatch(v -> v.getPaciente().getId().equals(idPaciente));
            }

            case ROLE_RESPONSAVEL -> {
                return usuarioLogado.getVinculosResponsaveis().stream().anyMatch(v -> v.getPaciente().getId().equals(idPaciente));
            }

            default -> { return false; }

        }
    }

}
