package br.com.upe.espectrum.services.paciente;
import br.com.upe.espectrum.dto.mappers.PacienteMapper;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService{
    private final PacienteRepository pacienteRepository;
    private final AdminRepository adminRepository;
    private final ResponsavelRepository responsavelRepository;
    private final TerapeutaRepository terapeutaRepository;
    private final PacienteMapper pacienteMapper;

    @Override
    @Transactional
    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteRequestDTO.nome());
        paciente.setDataNascimento(pacienteRequestDTO.dataNascimento());
        paciente.setGenero(pacienteRequestDTO.genero());
        paciente.setCpf(pacienteRequestDTO.cpf());
        Admin admin = adminRepository.findById(pacienteRequestDTO.adminId())
                .orElseThrow(() -> new RuntimeException("Admin não encontrado no banco de dados"));
        paciente.setAdmin(admin);

        return pacienteRepository.save(paciente);
    }

    @Override
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId) {
        List<Paciente> pacienteList = pacienteRepository.findByAdminId(adminId);
        return pacienteList.stream()
                // 🔴 O segredo está aqui: tiramos os parênteses e usamos '::'
                .map(pacienteMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

//    Apaguei pq os usuario foram feitos por composição
//    @Override
//    public List<PacienteResponseDTO> mostrarPacientesDoTerapeuta(UUID terapeutaId) {
//        List<Paciente> pacienteList = pacienteRepository.findByTerapeutaId(terapeutaId);
//        return pacienteList.stream().map(PacienteResponseDTO::new).collect(Collectors.toList());
//    }

    @Override
    public PacienteResponseDTO mostrarPaciente(UUID pacienteID) {
        Paciente paciente = pacienteRepository.findById(pacienteID)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado no banco de dados!"));
        return pacienteMapper.entityToResponseDto(paciente);
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
