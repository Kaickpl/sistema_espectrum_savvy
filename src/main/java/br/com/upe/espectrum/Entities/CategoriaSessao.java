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
public class CategoriaSessao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private List<String> Comentarios;

    @ManyToOne
    private CategoriaTemplete categoriaTemplete;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaSessao")
    private List<Comentario> comentarios;
}
