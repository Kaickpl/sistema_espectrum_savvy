package br.com.upe.espectrum.entities.enums;

public enum StatusCadastro {
    PENDENTE("Aguardando aprovação do administrador"),
    APROVADO("Cadastro aprovado"),
    REJEITADO("Cadastro rejeitado");

    private String descricao;

    StatusCadastro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
