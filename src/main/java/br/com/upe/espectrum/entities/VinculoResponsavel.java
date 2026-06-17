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
@Table(name = "vinculos-responsaveis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE vinculos-responsaveis SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class VinculoResponsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario usuario;

    @Column(name = "data_do_vinculo")
    private LocalDate dataVinculo = LocalDate.now();

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "grau_parentesco")
    private String grauParentesco;

}


