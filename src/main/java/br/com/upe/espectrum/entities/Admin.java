package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String registroProfissional;

    @OneToOne
    @MapsId
    private Usuario usuario;

    @OneToMany(mappedBy = "admin")
    private List<Paciente> paciente;


}
