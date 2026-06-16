package br.com.upe.espectrum.entities;

import br.com.upe.espectrum.entities.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeSessao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Pontuacao pontuacao;

    @ManyToOne
    private AtividadeTemplete atividadeTemplete;

    @ManyToOne
    private CategoriaSessao categoriaSessao;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;

    @ManyToOne
    private Usuario atualizadoPor;
}
