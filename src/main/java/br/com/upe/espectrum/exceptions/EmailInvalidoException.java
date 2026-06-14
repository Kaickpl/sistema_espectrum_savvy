package br.com.upe.espectrum.exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException(String message) {
        super(message);
    }
    public  EmailInvalidoException() {
        super("Email invalido");
    }
}
