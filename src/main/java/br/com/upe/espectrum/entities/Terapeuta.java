package br.com.upe.espectrum.entities;

import br.com.upe.espectrum.entities.enums.StatusCadastro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class Terapeuta {
    @Id
    @Column(name = "usuario_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "numero_matricula")
    String matricula;

    @Column(name = "periodo_do_curso")
    int periodo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cadastro", nullable = false)
    private StatusCadastro statusCadastro = StatusCadastro.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @Column(name = "is_active")
    private boolean isActive = true;
}
