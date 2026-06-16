package br.com.upe.espectrum.exceptions;

public class SenhasNaoConferemException extends RuntimeException {
    public SenhasNaoConferemException(String message) {
        super(message);
    }

    public SenhasNaoConferemException() {
      super("Campos senhas não são iguais");
    }
}
