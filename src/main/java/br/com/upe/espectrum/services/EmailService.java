package br.com.upe.espectrum.services;

public interface EmailService {
    void enviarEmail(String[] destinatarios, String assunto, String corpo);
}
