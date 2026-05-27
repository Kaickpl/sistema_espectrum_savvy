package br.com.upe.espectrum.entities;

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

    private LocalDateTime dataInicil = LocalDateTime.now();
    private LocalDateTime dataFinal;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LocalDateTime> datasSalvas;

    private Boolean status;// ajeitar nome do atributo

    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private List<Comentario> comentarios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocoloSessao")
    private  List<CategoriaSessao> categoriasSessao;

    @ManyToOne
    private ProtocoloTemplete protocoloTemplete;

    // many to one templete protocolo

}
