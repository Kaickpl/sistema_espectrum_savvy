package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String nomeAtividade;

    @OneToMany(mappedBy = "atividadeTemplete")
    private List<AtividadeSessao> atividadeSessao =  new ArrayList<>() ;

    @ManyToOne
    private CategoriaTemplete categoria;



}
