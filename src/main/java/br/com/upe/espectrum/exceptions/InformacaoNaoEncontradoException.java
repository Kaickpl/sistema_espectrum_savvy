package br.com.upe.espectrum.exceptions;

public class InformacaoNaoEncontradoException extends RuntimeException {
    public InformacaoNaoEncontradoException(String message) {
        super(message);
    }
    public InformacaoNaoEncontradoException() {
        super("Informação não encontrada.");
    }
}
