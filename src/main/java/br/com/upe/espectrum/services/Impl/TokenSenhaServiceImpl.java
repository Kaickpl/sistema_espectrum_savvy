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

@Service
@RequiredArgsConstructor
public class TokenSenhaServiceImpl implements TokenSenhaService {

    private final TokenSenhaRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final JavaMailSender mailSender;



    @Override
    @Transactional
    public void solicitarRecuperacao(String email) {

        if (usuarioRepository.findByEmail(email).isPresent()){
            String token = java.util.UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
            tokenRepository.deleteByEmail(email);
            tokenRepository.save(new TokenRecuperarSenha(email, token));
            enviarEmail(email, token);

        }


    }

    @Override
    public boolean verificarToken(String email, String token) {
        return tokenRepository.findByEmailAndToken(email, token)
                .map(t -> !t.isExpiredo())
                .orElse(false);
    }


    public void enviarEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Recuperação da sua conta do Socially Savvy");
        message.setText("Olá! Seu código de recuperação é: " + token + ". Ele expira em 30 minutos.");
        mailSender.send(message);
    }
}
