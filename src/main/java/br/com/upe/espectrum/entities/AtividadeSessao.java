package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.enums.*;
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
public class AtividadeSessao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Pontuacao pontuacao;

    @ManyToOne
    private AtividadeTemplete atividadeTemplete;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atividadeSessao")
    private List<Comentario> comentarios;

    @ManyToOne
    private CategoriaSessao categoriaSessao;
}
