package br.com.upe.espectrum.entities;
import br.com.upe.espectrum.entities.enums.GrauAutismo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private LocalDate dataNascimento;
    private String genero;
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_autismo")
    private GrauAutismo grauAutismo;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente")
    private List<ProtocoloSessao> protocoloSessaos = new ArrayList<>();

    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL)
    private List<VinculoTerapeuta> equipeMultiDisciplinar = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<VinculoEscolar> vinculosEscolar = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}