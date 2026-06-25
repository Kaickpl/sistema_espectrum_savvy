package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Terapeuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    List<Paciente> findByAdminId(UUID adminId);

    long countByAdminId(UUID adminId);


    @Query(value = "SELECT is_active FROM pacientes WHERE id = :id", nativeQuery = true)
    Optional<Boolean> verificarStatusConta(@Param("id") UUID id);


    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE pacientes SET is_active = :status WHERE id = :id", nativeQuery = true)
    void alterarStatusDiretoNoBanco(@Param("id") UUID id, @Param("status") boolean status);
}
