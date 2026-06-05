package br.com.upe.espectrum.entities;

import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocoloSessao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFinal;

    @OneToMany(
            mappedBy = "protocoloSessao",
            cascade = CascadeType.ALL)
    private List<HistoricoSalvamento> historicoSalvamentos =
            new ArrayList<>();


    @Enumerated(EnumType.STRING)
    private StatusProtocolo statusProtocolo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario criadoPor;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario finalizadoPor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private  List<CategoriaSessao> categoriasSessao = new ArrayList<>();

    @ManyToOne
    private ProtocoloTemplete protocoloTemplete;


}
