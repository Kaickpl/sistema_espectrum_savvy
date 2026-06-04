package br.com.upe.espectrum.controller;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.dtos.RequestDtos.CategoriaTempleteDto;
import br.com.upe.espectrum.dtos.ResponseDtos.CategoriaTempleteResponseDto;
import br.com.upe.espectrum.services.CategoriaTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaTempleteController {

    @Autowired
    private CategoriaTempleteService categoriaTempleteService;

    @PostMapping
    public ResponseEntity<CategoriaTempleteResponseDto> criarCategoria(@RequestBody CategoriaTempleteDto categoriaTempleteDto ) {
        CategoriaTemplete categoriaTemplete = categoriaTempleteService.criarCategoria(categoriaTempleteDto);
        if(categoriaTemplete == null){
            return ResponseEntity.badRequest().build();

        }
        return ResponseEntity.ok(new CategoriaTempleteResponseDto(categoriaTemplete));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaTempleteResponseDto>>BuscarTodosCategorias(){
        List<CategoriaTempleteResponseDto> categorias = categoriaTempleteService.BuscarTodos().stream().map(CategoriaTempleteResponseDto::new).toList();
        return ResponseEntity.ok(categorias);
    }




}
