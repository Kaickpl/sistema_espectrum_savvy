package br.com.upe.espectrum.services.paciente;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface PacienteService {
    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO);
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId);
    public List<PacienteResponseDTO> mostrarPacientesDoTerapeuta(UUID terapeutaId);
    public PacienteResponseDTO mostrarPaciente(UUID pacienteID);
}
