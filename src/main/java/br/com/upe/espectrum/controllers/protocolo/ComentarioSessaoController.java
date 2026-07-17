package br.com.upe.espectrum.controllers.protocolo;

import br.com.upe.espectrum.dto.requestDtos.ComentarioRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ComentarioResponseCategoriaDto;
import br.com.upe.espectrum.dto.responseDtos.ComentarioResponseDto;
import br.com.upe.espectrum.entities.Comentario;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.protocolo.ComentarioService;
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

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping("/sessao/{sessaoId}")
    public ResponseEntity<ComentarioResponseDto> comentarProtocolo(@PathVariable UUID sessaoId, @RequestBody ComentarioRequestDto dto) {

        UUID usuarioId = securityUtils.getCurrentUserId();

        Comentario comentario = comentarioService
                .adicionarComentarioProtocolo(sessaoId, usuarioId, dto.getComentario());
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ComentarioResponseDto(comentario));
    }

    @PostMapping("/categoria/{categoriaSessaoId}")
    public ResponseEntity<ComentarioResponseCategoriaDto> comentarCategoria(
            @PathVariable UUID categoriaSessaoId,
            @RequestBody ComentarioRequestDto dto) {

        UUID usuarioId = securityUtils.getCurrentUserId();

        Comentario comentario = comentarioService
                .adicionarComentarioCategoria(categoriaSessaoId, usuarioId, dto.getComentario());
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ComentarioResponseCategoriaDto(comentario));
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
    public ResponseEntity<List<ComentarioResponseCategoriaDto>> buscarComentariosCategoria(
            @PathVariable UUID categoriaSessaoId) {

        List<ComentarioResponseCategoriaDto> comentarios = comentarioService
                .buscarComentariosCategoria(categoriaSessaoId)
                .stream()
                .map(ComentarioResponseCategoriaDto::new)
                .toList();

        return ResponseEntity.ok(comentarios);
    }



}
