package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.responseDtos.ProfessorResumoResponseDto;
import br.com.upe.espectrum.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorResumoResponseDto>> listarProfessores() {
        return ResponseEntity.ok(professorService.listarProfessores());
    }
}
