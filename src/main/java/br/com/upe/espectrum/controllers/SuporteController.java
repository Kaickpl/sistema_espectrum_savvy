package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.SuporteRequestDto;
import br.com.upe.espectrum.services.SuporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suporte")
public class SuporteController {

    private final SuporteService suporteService;

    @PostMapping
    public ResponseEntity<Void> criarSolicitacao(@RequestBody SuporteRequestDto dto) {
        suporteService.criarSolicitacao(dto);
        return ResponseEntity.ok().build();
    }
}
