package br.com.upe.espectrum.controller;

import br.com.upe.espectrum.dtos.RequestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.dtos.RequestDtos.CategoriaTempleteDto;
import br.com.upe.espectrum.dtos.ResponseDtos.AtividadeTempleteResponseDto;
import br.com.upe.espectrum.dtos.ResponseDtos.CategoriaTempleteResponseDto;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.services.AtividadeTempleteService;
import br.com.upe.espectrum.services.CategoriaTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
