package br.com.upe.espectrum.controller;

import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.services.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> cadastrarPaciente(@RequestBody PacienteRequestDTO pacienteRequestDTO){
        Paciente paciente = pacienteService.cadastrarPaciente(pacienteRequestDTO);
        PacienteResponseDTO dto = new PacienteResponseDTO(paciente);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/todos/{adminId}")
    public ResponseEntity<List<PacienteResponseDTO>> mostrarTodosPacientes(@PathVariable UUID adminId){
        List<PacienteResponseDTO> lista = pacienteService.mostrarTodosPacientes(adminId);
        return ResponseEntity.ok(lista);
    }

//    @GetMapping("/{terapeutaId}/pacientes")
//    public ResponseEntity<List<PacienteResponseDTO>> mostrarPacientesDoTerapeuta(@PathVariable UUID terapeutaId){
//        List<PacienteResponseDTO> lista = pacienteService.mostrarPacientesDoTerapeuta(terapeutaId);
//        return ResponseEntity.ok(lista);
//    }
//
    @GetMapping("/{pacienteId}")
    public ResponseEntity<PacienteResponseDTO> mostrarPaciente(@PathVariable UUID pacienteId){
        PacienteResponseDTO pacienteResponseDTO = pacienteService.mostrarPaciente(pacienteId);
        return ResponseEntity.ok(pacienteResponseDTO);
    }
}
