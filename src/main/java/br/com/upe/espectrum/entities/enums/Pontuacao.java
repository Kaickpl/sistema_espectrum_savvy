package br.com.upe.espectrum.entities.enums;

public enum Pontuacao {
   RARAMENTE_OU_NUNCA(1),
   DEMONSTRA(2),
   PODE_DEMONSTRAR(3),
   DEMONSTRA_CONSISTENTEMENTE(4),
   NAO_APLICAVEL(0);




    private final int valor;


   Pontuacao(int valor) {
       this.valor = valor;
   }
   public int getValor() {
       return this.valor;
   }
}
