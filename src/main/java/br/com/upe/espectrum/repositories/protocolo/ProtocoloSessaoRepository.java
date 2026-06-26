package br.com.upe.espectrum.repositories.protocolo;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProtocoloSessaoRepository extends JpaRepository<ProtocoloSessao, UUID> {
    List<ProtocoloSessao> findAllByPacienteId(UUID pacienteId);
    @Query("SELECT p FROM ProtocoloSessao p LEFT JOIN FETCH p.categoriasSessao WHERE p.id = :id")
    Optional<ProtocoloSessao> findByIdWithCategorias(@Param("id") UUID id);

    long countByPaciente_Admin_IdAndStatusProtocolo(UUID adminId, StatusProtocolo statusProtocolo);
}
