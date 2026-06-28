package br.com.upe.espectrum.services;

import org.springframework.stereotype.Service;

@Service
public interface TokenSenhaService {
    void solicitarRecuperacao(String email);
    boolean verificarToken(String email ,String token);
}
