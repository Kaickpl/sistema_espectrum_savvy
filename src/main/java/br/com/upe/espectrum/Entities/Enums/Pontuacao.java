package br.com.upe.espectrum.Entities.Enums;

public enum Pontuacao {
   RARAMENTE_OU_NUNCA(0),
   DEMONSTRA(1),
   PODE_DEMONSTRAR(2),
   DEMONSTRA_CONSISTENTEMENTE(3),
   NAO_APLICAVEL(4);




    private final int valor;


   Pontuacao(int valor) {
       this.valor = valor;
   }
   public int getValor() {
       return this.valor;
   }
}
