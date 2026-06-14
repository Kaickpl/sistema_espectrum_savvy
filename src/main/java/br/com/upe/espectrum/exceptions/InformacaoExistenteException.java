package br.com.upe.espectrum.exceptions;

public class InformacaoExistenteException extends RuntimeException {
    public InformacaoExistenteException(String message) {
        super(message);
    }

    public InformacaoExistenteException() {
        super("Informação existente!");
    }
}
