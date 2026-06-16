package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.AtividadeSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AtividadeSessaoRepository extends JpaRepository<AtividadeSessao, UUID> {
    List<AtividadeSessao> findAllByCategoriaSessaoId(UUID categoriaSessaoId);

}
