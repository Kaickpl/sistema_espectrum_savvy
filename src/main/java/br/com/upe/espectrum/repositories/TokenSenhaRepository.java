package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.TokenRecuperarSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenSenhaRepository extends JpaRepository<TokenRecuperarSenha, UUID> {
    Optional<TokenRecuperarSenha> findByEmailAndToken(String email, String token);
    Optional<TokenRecuperarSenha> findByEmail(String email);
    void deleteByEmail(String email);
}