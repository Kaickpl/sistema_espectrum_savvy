package br.com.upe.espectrum.entities;

import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private LocalDateTime dataInicil;

    private LocalDateTime dataFinal;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> datasSalvas;

    @Enumerated(EnumType.STRING)
    private StatusProtocolo statusProtocolo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario criadoPor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private List<Comentario> comentarios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private  List<CategoriaSessao> categoriasSessao;

    @ManyToOne
    private ProtocoloTemplete protocoloTemplete;


}
