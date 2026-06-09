package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.CategoriaSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoriaSessaoRepository extends JpaRepository<CategoriaSessao, UUID> {
}
