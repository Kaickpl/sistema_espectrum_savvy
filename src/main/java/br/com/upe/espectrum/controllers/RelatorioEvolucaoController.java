package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.responseDtos.RelatorioEvolucaoResponseDto;
import br.com.upe.espectrum.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/relatorio")
@CrossOrigin(origins = "*")
public class RelatorioEvolucaoController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/evolucao/paciente/{pacienteId}")
    public ResponseEntity<RelatorioEvolucaoResponseDto> buscarRelatorioEvolucao(
            @PathVariable UUID pacienteId,
            @RequestParam(name = "meses", defaultValue = "6") int meses) {

        RelatorioEvolucaoResponseDto relatorio = relatorioService.gerarRelatorioEvolucao(pacienteId, meses);
        return ResponseEntity.ok(relatorio);
    }
}
