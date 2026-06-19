package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vinculos_terapeutas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE vinculos_terapeutas SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class VinculoTerapeuta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "data_do_vinculo")
    private LocalDate dataVinculo = LocalDate.now();

    @Column(name = "is_active")
    private boolean isActive = true;

    //poderia ativar uma função de ter um tempo máximo de vinculo pro adm nao precisar ficar desvinculando tudo.
}
