package br.com.upe.espectrum.entities.enums;

public enum Pontuacao {
    NAO_APLICAVEL(0),
    RARAMENTE_OU_NUNCA(1),
   DEMONSTRA(2),
   PODE_DEMONSTRAR(3),
   DEMONSTRA_CONSISTENTEMENTE(4);




    private final int valor;


   Pontuacao(int valor) {
       this.valor = valor;
   }
   public int getValor() {
       return this.valor;
   }
}
