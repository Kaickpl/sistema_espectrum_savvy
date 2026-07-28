package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.VinculoResponsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VinculoResponsavelRepository extends JpaRepository<VinculoResponsavel, UUID> {
    List<VinculoResponsavel> findByUsuarioId(UUID usuarioId);
}
