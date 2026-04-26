package br.com.upe.espectrum.Entities;

import br.com.upe.espectrum.Entities.Enums.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE usuarios SET is_active = false WHERE u = ?")
@SQLRestriction("is_activate = true")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;
    private String senha;

    private String cpf;
    private String nome;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private Perfil tipo;

    private boolean isActive = true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Terapeuta perfilTerapeuta;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Admin perfilAmin;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Responsavel perfilResponsavel;

}
