package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.entities.enums.StatusCadastro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TerapeutaRepository extends JpaRepository <Terapeuta, UUID> {
    List<Terapeuta> findByStatusCadastro(StatusCadastro status);
    List<Terapeuta> findByAdminId(UUID adminId);
    List<Terapeuta> findByStatusCadastroAndAdminId(StatusCadastro status, UUID adminId);

    @Query(value = "SELECT * FROM terapeuta WHERE usuario_id = :id", nativeQuery = true)
    Optional<Terapeuta> encontrarComOuSemFiltro(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE terapeuta SET is_active = :status WHERE usuario_id = :id", nativeQuery = true)
    void alterarStatusDiretoNoBanco(@Param("id") UUID id, @Param("status") boolean status);
}
