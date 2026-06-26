package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.VinculoRequestDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteVinculadoResponseDto;
import br.com.upe.espectrum.services.VinculoGeralService;
import br.com.upe.espectrum.services.VinculoTerapeutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vinculos")
@RequiredArgsConstructor
public class VinculoGeralController {

    private final VinculoGeralService vinculoGeralService;
    private final VinculoTerapeutaService vinculoTerapeutaService;

    @PostMapping
    public ResponseEntity<Void> criarVinculo(@Valid @RequestBody VinculoRequestDto dto) {

        vinculoGeralService.criarVinculo(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/terapeuta/{idTerapeuta}/pacientes")
    public ResponseEntity<List<PacienteVinculadoResponseDto>> listarPacientesVinculados(@PathVariable UUID idTerapeuta) {
        return ResponseEntity.ok(vinculoTerapeutaService.listarPacientesVinculados(idTerapeuta));
    }

    @GetMapping("/terapeuta/{idTerapeuta}/pacientes-disponiveis")
    public ResponseEntity<List<PacienteResumoResponseDto>> listarPacientesDisponiveis(@PathVariable UUID idTerapeuta) {
        return ResponseEntity.ok(vinculoTerapeutaService.listarPacientesDisponiveis(idTerapeuta));
    }

    @GetMapping("/meus-pacientes")
    public ResponseEntity<List<PacienteResumoResponseDto>> listarMeusPacientesVinculados() {
        return ResponseEntity.ok(vinculoTerapeutaService.listarMeusPacientesVinculados());
    }

    @DeleteMapping("/{idVinculo}")
    public ResponseEntity<Void> desvincular(@PathVariable UUID idVinculo) {
        vinculoTerapeutaService.desvincular(idVinculo);
        return ResponseEntity.noContent().build();
    }
}
