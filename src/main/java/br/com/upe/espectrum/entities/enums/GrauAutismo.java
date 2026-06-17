package br.com.upe.espectrum.entities.enums;

public enum GrauAutismo {
    NIVEL_1("Nível 1 - Suporte Leve"),
    NIVEL_2("Nível 2 - Suporte Moderado"),
    NIVEL_3("Nível 3 - Suporte Substancial");

    private String descricao;

    GrauAutismo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}