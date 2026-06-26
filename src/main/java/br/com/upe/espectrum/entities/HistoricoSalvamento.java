package br.com.upe.espectrum.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoSalvamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataSalvamento;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private ProtocoloSessao protocoloSessao;

    @Column(columnDefinition = "TEXT")
    private String dadosSessaoJson;
}
