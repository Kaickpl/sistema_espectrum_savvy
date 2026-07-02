package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.PacienteRequestDTO;
import br.com.upe.espectrum.dto.responseDtos.PacienteResponseDTO;
import br.com.upe.espectrum.services.paciente.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPaciente(@PathVariable UUID id){
        PacienteResponseDTO response = pacienteService.mostrarPaciente(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<PacienteResponseDTO>> buscarPacientesPorUsuario(){
        List<PacienteResponseDTO> response = pacienteService.listarPacientesDoUsuario();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> editarPaciente(@PathVariable UUID id, @Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO response = pacienteService.editarPaciente(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarPaciente(@PathVariable UUID id) {
        pacienteService.desativarContaPaciente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarPaciente(@PathVariable UUID id) {
        pacienteService.reativarContaPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
