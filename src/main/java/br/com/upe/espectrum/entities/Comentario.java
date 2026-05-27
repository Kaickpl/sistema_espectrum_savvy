package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String comentario;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    private AtividadeSessao atividadeSessao;

    @ManyToOne
    private CategoriaSessao categoriaSessao;

    @ManyToOne
    private ProtocoloSessao protocoloSessao;


}
