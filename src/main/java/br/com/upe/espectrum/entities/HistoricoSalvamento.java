package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoSalvamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataSalvamento;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private ProtocoloSessao protocoloSessao;

}
