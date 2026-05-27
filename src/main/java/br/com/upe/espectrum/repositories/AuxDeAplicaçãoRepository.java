package br.com.upe.espectrum.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuxDeAplicaçãoRepository extends JpaRepository<AuxiliarDeAplicação, UUID> {
}
