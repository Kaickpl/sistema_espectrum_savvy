package br.com.upe.espectrum.entities.enums;

public enum CategoriaSuporte {
    LOGIN_CONTA("Login e Conta"),
    FUNCIONAMENTO_APP("Funcionamento do App"),
    RESULTADOS_HISTORICO("Resultados e Histórico"),
    OUTROS("Outros");

    private String descricao;

    CategoriaSuporte(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
