package br.com.upe.espectrum.controllers.protocolo;

import br.com.upe.espectrum.dto.responseDtos.ProtocoloSessaoResponseDto;
import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.protocolo.ProtocoloSessaoService;
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

    @Autowired
    private SecurityUtils securityUtils;

    @PostMapping("/iniciar/paciente/{pacienteId}/")
    public ResponseEntity<ProtocoloSessaoResponseDto> iniciarSessao(@PathVariable UUID pacienteId) {

        UUID usuarioId = securityUtils.getCurrentUserId();

        ProtocoloSessao sessao = protocoloSessaoService.iniciarProtocoloSessao(usuarioId, pacienteId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }


    @PutMapping("/{sessaoId}/finalizar")
    public ResponseEntity<ProtocoloSessaoResponseDto> finalizarSessao(@PathVariable UUID sessaoId) {

        UUID usuarioId = securityUtils.getCurrentUserId();

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

    @PostMapping("/{sessaoId}/salvar/")
    public ResponseEntity<ProtocoloSessaoResponseDto> salvarProgresso(@PathVariable UUID sessaoId) {

        UUID usuarioId = securityUtils.getCurrentUserId();

        ProtocoloSessao sessao = protocoloSessaoService.salvarProgresso(sessaoId, usuarioId);
        if (sessao == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ProtocoloSessaoResponseDto(sessao));
    }


}
