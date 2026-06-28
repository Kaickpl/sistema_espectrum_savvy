package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.requestDtos.LoginRequestDto;
import br.com.upe.espectrum.dto.responseDtos.LoginResponseDto;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.security.TokenConfig;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
        Authentication authentication = authenticationManager.authenticate(userAndPass);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(usuario);
        return ResponseEntity.ok(new LoginResponseDto(token, usuario.getId(), usuario.getNome(), usuario.getTipo().toString()));

    }
}
