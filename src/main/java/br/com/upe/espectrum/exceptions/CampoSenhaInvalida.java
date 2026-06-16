package br.com.upe.espectrum.exceptions;

public class CampoSenhaInvalida extends RuntimeException {
  public CampoSenhaInvalida(String message) {
    super(message);
  }

  public CampoSenhaInvalida() {
    super("Formato senha Invalida. A senha deve conter no mínimo 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos. ");
  }
}
