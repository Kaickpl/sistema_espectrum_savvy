package br.com.upe.espectrum.exceptions;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String message) {
        super(message);
    }
    public CampoObrigatorioException() {
        super("Erro: Campo obrigatório não informado.");
    }
}
