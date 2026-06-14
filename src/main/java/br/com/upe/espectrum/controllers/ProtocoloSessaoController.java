package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.responseDtos.ProtocoloSessaoResponseDto;
import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.services.ProtocoloSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/sessao")
public class ProtocoloSessaoController {

    @Autowired
    private ProtocoloSessaoService protocoloSessaoService;

    @PostMapping("/iniciar/paciente/{pacienteId}/usuario/{usuarioId}")
    public ResponseEntity<ProtocoloSessaoResponseDto> iniciarSessao(@PathVariable UUID pacienteId, @PathVariable UUID usuarioId) {

        ProtocoloSessao sessao = protocoloSessaoService.iniciarProtocoloSessao(usuarioId, pacienteId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }


    @PutMapping("/{sessaoId}/finalizar/usuario/{usuarioId}")
    public ResponseEntity<ProtocoloSessaoResponseDto> finalizarSessao(@PathVariable UUID sessaoId, @PathVariable UUID usuarioId) {

        ProtocoloSessao sessao = protocoloSessaoService.finalizarProtocoloSessao(usuarioId, sessaoId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }

    @GetMapping("/{sessaoId}")
    public ResponseEntity<ProtocoloSessaoResponseDto> buscarSessao(@PathVariable UUID sessaoId) {

        ProtocoloSessao sessao = protocoloSessaoService.buscarProtocoloSessao(sessaoId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ProtocoloSessaoResponseDto>> listarPorPaciente( @PathVariable UUID pacienteId) {

        List<ProtocoloSessaoResponseDto> sessoes = protocoloSessaoService
                .buscarTodosPorPaciente(pacienteId)
                .stream()
                .map(ProtocoloSessaoResponseDto::new)
                .toList();
        if (sessoes.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(sessoes);
    }

    @PostMapping("/{sessaoId}/salvar/usuario/{usuarioId}")
    public ResponseEntity<ProtocoloSessaoResponseDto> salvarProgresso(@PathVariable UUID sessaoId, @PathVariable UUID usuarioId) {

        ProtocoloSessao sessao = protocoloSessaoService.salvarProgresso(sessaoId, usuarioId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }


}
