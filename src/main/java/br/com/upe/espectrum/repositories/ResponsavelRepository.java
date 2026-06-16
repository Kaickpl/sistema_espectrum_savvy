package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Responsavel;
import br.com.upe.espectrum.entities.Terapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, UUID> {
    @Query(value = "SELECT * FROM responsavel WHERE id = :id", nativeQuery = true)
    Optional<Responsavel> encontrarComOuSemFiltro(@Param("id") UUID id);
}
