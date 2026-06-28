package br.com.upe.espectrum.controllers;


import br.com.upe.espectrum.dto.responseDtos.ValidacaoDto;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.exceptions.OperacaoNaoPermitida;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.TokenSenhaService;
import br.com.upe.espectrum.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

record RedefinirSenhaDTO(String email, String token, String novaSenha) {}

@RestController
@RequestMapping("trocarSenha")
@RequiredArgsConstructor
public class TrocarSenhaController {

    private final TokenSenhaService tokenSenhaService;
    private final UsuarioService usuarioService;



    @PostMapping("/solicitar-reset")
    public ResponseEntity<String> solicitar(@RequestParam String email) {
        tokenSenhaService.solicitarRecuperacao(email);
        // Sempre retorna sucesso, mesmo se o e-mail não existir (segurança silênciosa)
        return ResponseEntity.ok("Se o e-mail existir, você receberá o código.");
    }

    // Rota: /api/auth/validar-token
    @PostMapping("/validar-token")
    public ResponseEntity<String> validar(@RequestBody ValidacaoDto dto) {
        if (tokenSenhaService.verificarToken(dto.email(), dto.token())) {
            return ResponseEntity.ok("Token válido.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaDTO dto) {
        if (!tokenSenhaService.verificarToken(dto.email(), dto.token())) {
            throw new OperacaoNaoPermitida("Token inválido ou expirado.");
        }

        Usuario usuario = usuarioService.BuscarEmail(dto.email());
        usuarioService.atualizarSenhaViaToken(usuario.getId(), dto.novaSenha());

        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}
