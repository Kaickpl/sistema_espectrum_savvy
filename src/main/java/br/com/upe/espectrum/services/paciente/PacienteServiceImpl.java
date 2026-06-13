package br.com.upe.espectrum.services.paciente;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService{
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ResponsavelRepository responsavelRepository;
    @Autowired
    TerapeutaRepository terapeutaRepository;

    @Override
    @Transactional
    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteRequestDTO.getNome());
        paciente.setDataNascimento(pacienteRequestDTO.getDataNascimento());
        paciente.setGenero(pacienteRequestDTO.getGenero());
        paciente.setCpf(pacienteRequestDTO.getCpf());
        Admin admin = adminRepository.findById(pacienteRequestDTO.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin não encontrado no banco de dados"));
        paciente.setAdmin(admin);

        return pacienteRepository.save(paciente);
    }

    @Override
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId) {
        List<Paciente> pacienteList = pacienteRepository.findByAdminId(adminId);
        return pacienteList.stream().map(PacienteResponseDTO::new).collect(Collectors.toList());
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
        return new PacienteResponseDTO(paciente);
    }
}
