package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.mappers.TerapeutaMapper;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.services.TerapeutaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terapeuta")
public class TerapeutaController {

    private final TerapeutaService terapeutaService;
    private final TerapeutaMapper terapeutaMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TerapeutaResponseDto> buscarTerapeuta(@PathVariable UUID id) {
        TerapeutaResponseDto response = terapeutaService.buscarTerapeuta(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarTerapeuta(@Valid @PathVariable UUID id){
        terapeutaService.desativarContaTerapeuta(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<TerapeutaResponseDto> reativarTerapeuta(@Valid @PathVariable UUID id){
        terapeutaService.reativarContaTerapeuta(id);
        TerapeutaResponseDto response =terapeutaService.buscarTerapeuta(id);
        return ResponseEntity.ok(response);
    }

}
