package br.com.upe.espectrum.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String Categoria;
    private String descricaoCategoria;
}
