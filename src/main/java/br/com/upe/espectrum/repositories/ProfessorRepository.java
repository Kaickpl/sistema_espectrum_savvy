package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository <Professor, UUID> {
    @Query(value = "SELECT * FROM professor WHERE id = :id", nativeQuery = true)
    Optional<Professor> encontrarComOuSemFiltro(@Param("id") UUID id);
}
