package br.com.upe.espectrum.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTemplete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nomeCategoria;
    private String descricaoCategoria;

    @ManyToOne
    private ProtocoloTemplete protocolo;

    @OneToMany(mappedBy = "categoriaTemplete")
    private List<CategoriaSessao> categoriaSessao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<AtividadeTemplete> atividades;




}
