package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.Enums.Perfil;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;
    private String senha;

    private String nome;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Perfil tipo;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Terapeuta perfilTerapeuta;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Admin perfilAmin;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Responsavel perfilResponsavel;

}
