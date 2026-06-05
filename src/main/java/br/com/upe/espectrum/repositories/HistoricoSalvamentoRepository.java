package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.HistoricoSalvamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoricoSalvamentoRepository extends JpaRepository<HistoricoSalvamento, UUID> {
}
