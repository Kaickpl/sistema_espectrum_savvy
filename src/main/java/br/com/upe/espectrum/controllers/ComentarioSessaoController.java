package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.ComentarioRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ComentarioResponseDto;
import br.com.upe.espectrum.entities.Comentario;
import br.com.upe.espectrum.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/comentario")
public class ComentarioSessaoController {

    @Autowired
    ComentarioService comentarioService;

    @PostMapping("/sessao/{sessaoId}/usuario/{usuarioId}")
    public ResponseEntity<ComentarioResponseDto> comentarProtocolo(
            @PathVariable UUID sessaoId,
            @PathVariable UUID usuarioId,
            @RequestBody ComentarioRequestDto dto) {

        Comentario comentario = comentarioService
                .adicionarComentarioProtocolo(sessaoId, usuarioId, dto.getComentario());
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ComentarioResponseDto(comentario));
    }

    @PostMapping("/categoria/{categoriaSessaoId}/usuario/{usuarioId}")
    public ResponseEntity<ComentarioResponseDto> comentarCategoria(
            @PathVariable UUID categoriaSessaoId,
            @PathVariable UUID usuarioId,
            @RequestBody ComentarioRequestDto dto) {

        Comentario comentario = comentarioService
                .adicionarComentarioCategoria(categoriaSessaoId, usuarioId, dto.getComentario());
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ComentarioResponseDto(comentario));
    }


    @GetMapping("/sessao/{sessaoId}")
    public ResponseEntity<List<ComentarioResponseDto>> buscarComentariosProtocolo(
            @PathVariable UUID sessaoId) {

        List<ComentarioResponseDto> comentarios = comentarioService
                .buscarComentariosProtocolo(sessaoId)
                .stream()
                .map(ComentarioResponseDto::new)
                .toList();

        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/categoria/{categoriaSessaoId}")
    public ResponseEntity<List<ComentarioResponseDto>> buscarComentariosCategoria(
            @PathVariable UUID categoriaSessaoId) {

        List<ComentarioResponseDto> comentarios = comentarioService
                .buscarComentariosCategoria(categoriaSessaoId)
                .stream()
                .map(ComentarioResponseDto::new)
                .toList();

        return ResponseEntity.ok(comentarios);
    }



}
