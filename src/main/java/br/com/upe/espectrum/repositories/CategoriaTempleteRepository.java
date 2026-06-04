package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaTempleteRepository extends JpaRepository<CategoriaTemplete, UUID> {
    CategoriaTemplete findByNomeCategoriaIgnoreCase(String nomeCategoria);


}
