package br.com.upe.espectrum.controllers;
import br.com.upe.espectrum.dto.requestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.dto.responseDtos.AtividadeTempleteResponseDto;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import br.com.upe.espectrum.services.AtividadeTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/atividade")
public class AtividadeTempleteController {

    @Autowired
    private AtividadeTempleteService atividadeTempleteService;


    @PostMapping("/{categoriaId}")
    public ResponseEntity<AtividadeTempleteResponseDto> criar(
            @PathVariable UUID categoriaId,
            @RequestBody AtividadetempleteDto dto){
        AtividadeTemplete atividadeTemplete = atividadeTempleteService.criarAtividade(dto, categoriaId);
        if(atividadeTemplete == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(
                new AtividadeTempleteResponseDto(
                        atividadeTemplete
                )
        );
    }
}
