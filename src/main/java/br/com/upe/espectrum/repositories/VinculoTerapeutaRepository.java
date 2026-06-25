package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VinculoTerapeutaRepository extends JpaRepository<VinculoTerapeuta, UUID> {

    @Query(value = "SELECT * FROM vinculo_terapeuta WHERE id = :id", nativeQuery = true)
    Optional<VinculoTerapeuta> encontrarComOuSemFiltro(@Param("id") UUID id);

    long countByUsuarioId(UUID usuarioId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE vinculo_terapeuta SET is_active = :status WHERE usuario_id = :id", nativeQuery = true)
    void alterarStatusDiretoNoBanco(@Param("id") UUID id, @Param("status") boolean status);
}
