package br.com.upe.espectrum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRecuperarSenha {
    @Id
    @GeneratedValue
    private UUID id;

    private  String email;
    private String token;
    private LocalDateTime dataExpiracao;
    private int tentativas;

    private static final int MAX_TENTATIVAS = 5;

    public  TokenRecuperarSenha( String email, String token) {
        this.email = email;
        this.token = token;
        this.dataExpiracao = LocalDateTime.now().plusMinutes(15);
        this.tentativas = 0;
    }

    public boolean isExpiredo() {
        return LocalDateTime.now().isAfter(dataExpiracao);
    }

    public boolean isBloqueado() {
        return tentativas >= MAX_TENTATIVAS;
    }

    public void incrementarTentativa() {
        this.tentativas++;
    }
}
