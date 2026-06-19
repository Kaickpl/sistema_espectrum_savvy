package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.VinculoRequestDto;
import br.com.upe.espectrum.services.VinculoGeralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vinculos")
@RequiredArgsConstructor
public class VinculoGeralController {

    private final VinculoGeralService vinculoGeralService;

    @PostMapping
    public ResponseEntity<Void> criarVinculo(@Valid @RequestBody VinculoRequestDto dto) {

        vinculoGeralService.criarVinculo(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}