package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, UUID> {
    List<Comentario> findAllByCategoriaSessaoId(UUID categoriaId);
    List<Comentario> findAllByProtocoloSessaoId(UUID protocoloSessaoId);
}
