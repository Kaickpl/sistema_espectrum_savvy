package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.Enums.Pontuacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeTemplete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String NomeAtividade;
    private String Descricao;

    @OneToMany(mappedBy = "atividadeTemplete")
    private List<AtividadeSessao> atividadeSessao =  new ArrayList<>() ;

    @ManyToOne
    private CategoriaTemplete categoria;



}
