package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "vinculos_pacientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE vinculos_pacientes SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class VinculoPaciente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "data_do_vinculo")
    private LocalDate dataVinculo = LocalDate.now();

    private boolean isActive = true;

    //poderia ativar uma função de ter um tempo máximo de vinculo pro adm nao precisar ficar desvinculando tudo.
}
