package br.com.upe.espectrum.controllers.protocolo;

import br.com.upe.espectrum.dto.requestDtos.AtualizarPontuacaoRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AtividadeSessaoResponseDto;
import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.protocolo.AtividadeSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/atividade")
public class AtividadeSessaoController {

    @Autowired
    private AtividadeSessaoService atividadeSessaoService;

    @Autowired
    private SecurityUtils securityUtils;

    @PutMapping("/{atividadeId}")
    public ResponseEntity<AtividadeSessaoResponseDto> atualizarPontuacao(@PathVariable UUID atividadeId, @RequestBody AtualizarPontuacaoRequestDto dto) {

        UUID usuarioId = securityUtils.getCurrentUserId();

        AtividadeSessao atividade = atividadeSessaoService.atualizarPontuacao(atividadeId, usuarioId, dto.getPontuacao());
        if (atividade == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AtividadeSessaoResponseDto(atividade));
    }

    @GetMapping("/{atividadeId}")
    public ResponseEntity<AtividadeSessaoResponseDto> buscarAtividade(@PathVariable UUID atividadeId) {

        AtividadeSessao atividade = atividadeSessaoService.buscarAtividade(atividadeId);
        if (atividade == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new AtividadeSessaoResponseDto(atividade));
    }

    @GetMapping("/categoria/{categoriaSessaoId}")
    public ResponseEntity<List<AtividadeSessaoResponseDto>> buscarTodasPorCategoria(@PathVariable UUID categoriaSessaoId) {

        List<AtividadeSessaoResponseDto> atividades = atividadeSessaoService
                .buscarTodasPorCategoria(categoriaSessaoId)
                .stream()
                .map(AtividadeSessaoResponseDto::new)
                .toList();

        return ResponseEntity.ok(atividades);
    }

}
