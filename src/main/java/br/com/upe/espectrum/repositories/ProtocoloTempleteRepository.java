package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.entities.ProtocoloTemplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProtocoloTempleteRepository extends JpaRepository<ProtocoloTemplete, UUID> {
}
