package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.AtividadeTemplete;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AtividadeTempleteRepository extends CrudRepository<AtividadeTemplete, UUID> {
    AtividadeTemplete findByNomeAtividadeIgnoreCase(String nomeAtividade);
}
