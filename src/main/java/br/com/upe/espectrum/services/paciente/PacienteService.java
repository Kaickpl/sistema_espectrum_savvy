package br.com.upe.espectrum.services.paciente;
import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface PacienteService {
    //    public Paciente cadastrarPaciente(PacienteRequestDTO pacienteRequestDTO);
    public boolean verificarSePacientePertenceAoUsuario(UUID idPaciente);
    public List<PacienteResponseDTO> mostrarTodosPacientes(UUID adminId);
    public PacienteResponseDTO mostrarPaciente(UUID pacienteID);
    public List<PacienteResponseDTO> listarPacientesDoUsuario();
    public void reativarContaPaciente(UUID pacienteId);
    public void desativarContaPaciente(UUID pacienteId);
    public Paciente mostrarPacienteEntity(UUID pacienteID);
    public PacienteResponseDTO editarPaciente(UUID pacienteId, PacienteRequestDTO dto);
}
