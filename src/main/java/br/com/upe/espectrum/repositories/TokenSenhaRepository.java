package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.TokenRecuperarSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenSenhaRepository extends JpaRepository<TokenRecuperarSenha, Long> {
    Optional<TokenRecuperarSenha> findByEmailAndToken(String email, String token);
    void deleteByEmail(String email);
}