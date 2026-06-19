package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE vinculo_escolar SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
@Table(name = "vinculo_escolar")
public class VinculoEscolar {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "ano_letivo")
    private String anoLetivo;

    private boolean isActive = false;

    @Column(name = "data_inicio_vinculo")
    private LocalDate dataInicio = LocalDate.now();
}
