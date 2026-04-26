package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.Enums.Pontuacao;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeTemplete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String NomeAtv;
    private String Descricao;
}
