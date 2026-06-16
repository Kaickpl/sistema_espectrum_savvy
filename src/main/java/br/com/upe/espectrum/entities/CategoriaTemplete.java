package br.com.upe.espectrum.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

import java.util.List;

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

    @ManyToOne
    private ProtocoloTemplete protocolo;

    @OneToMany(mappedBy = "categoriaTemplete")
    private List<CategoriaSessao> categoriaSessao = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private List<AtividadeTemplete> atividades = new ArrayList<>();




}
