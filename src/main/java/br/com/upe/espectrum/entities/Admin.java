package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_active = true")
public class Admin {
    @Id
    private UUID id;

    private String registroProfissional;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "admin")
    private List<Paciente> paciente;

    @OneToMany(mappedBy = "admin")
    private List<Terapeuta> terapeutas;

    @Column(name = "is_active")
    private boolean isActive = true;

}
