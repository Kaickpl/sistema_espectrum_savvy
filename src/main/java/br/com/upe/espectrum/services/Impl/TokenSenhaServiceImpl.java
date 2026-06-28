package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.TokenRecuperarSenha;
import br.com.upe.espectrum.repositories.TokenSenhaRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.TokenSenhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class TokenSenhaServiceImpl implements TokenSenhaService {

    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TOKEN_LENGTH = 6;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final TokenSenhaRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender mailSender;



    @Override
    @Transactional
    public void solicitarRecuperacao(String email) {

        if (usuarioRepository.findByEmail(email).isPresent()){
            String token = gerarToken();
            tokenRepository.deleteByEmail(email);
            tokenRepository.save(new TokenRecuperarSenha(email, token));
            enviarEmail(email, token);

        }


    }

    private String gerarToken() {
        StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(TOKEN_CHARS.charAt(SECURE_RANDOM.nextInt(TOKEN_CHARS.length())));
        }
        return sb.toString();
    }

    @Override
    @Transactional
    public boolean verificarToken(String email, String token) {
        var tokenOpt = tokenRepository.findByEmail(email);
        if (tokenOpt.isEmpty()) {
            return false;
        }

        TokenRecuperarSenha tokenRecuperarSenha = tokenOpt.get();
        if (tokenRecuperarSenha.isExpiredo() || tokenRecuperarSenha.isBloqueado()) {
            return false;
        }

        if (!tokenRecuperarSenha.getToken().equals(token)) {
            tokenRecuperarSenha.incrementarTentativa();
            tokenRepository.save(tokenRecuperarSenha);
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void invalidarToken(String email) {
        tokenRepository.deleteByEmail(email);
    }


    public void enviarEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperação da sua conta do Socially Savvy");
        message.setText("Olá! Seu código de recuperação é: " + token + ". Ele expira em 30 minutos.");
        mailSender.send(message);
    }
}
