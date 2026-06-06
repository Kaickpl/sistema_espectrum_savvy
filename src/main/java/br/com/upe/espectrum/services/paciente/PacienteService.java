package br.com.upe.espectrum.services.paciente;

import br.com.upe.espectrum.dtos.RequestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dtos.ResponseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface PacienteService {
    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO);
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId);
    public List<PacienteResponseDTO> mostrarPacientesDoTerapeuta(UUID terapeutaId);
}
