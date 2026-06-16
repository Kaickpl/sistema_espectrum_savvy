package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    @Query(value = "SELECT * FROM admin WHERE id = :id", nativeQuery = true)
    Optional<Admin> encontrarComOuSemFiltro(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE admin SET is_active = :status WHERE id = :id", nativeQuery = true)
    void alterarStatusDiretoNoBanco(@Param("id") UUID id, @Param("status") boolean status);
}
