package br.com.upe.espectrum.repositories;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    boolean existsByNumeroTelefone(String numeroTelefone);
    boolean existsByCpf(String cpf);
    @Query(value = "SELECT * FROM usuario WHERE id = :id", nativeQuery = true)
    Optional<Usuario> encontrarComOuSemFiltro(@Param("id") UUID id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET is_active = :status WHERE id = :id", nativeQuery = true)
    void alterarStatusDiretoNoBanco(@Param("id") UUID id, @Param("status") boolean status);
}
