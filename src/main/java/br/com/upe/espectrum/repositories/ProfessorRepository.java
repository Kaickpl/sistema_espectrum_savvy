package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository <Professor, UUID> {
}
