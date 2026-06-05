package br.com.upe.espectrum.entities;

import br.com.upe.espectrum.entities.enums.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE usuarios SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "telefone", unique = true, nullable = false)
    private String numeroTelefone;

    @Column(name = "email", unique = true)
    private String email;

    private String senha;

    private String cpf;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Perfil tipo;

    private boolean isActive = true;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Terapeuta perfilTerapeuta;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Admin perfilAdmin;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Responsavel perfilResponsavel;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Professor professor;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<VinculoPaciente> vinculoPacientes;

    @OneToMany(mappedBy = "criadoPor")
    private List<ProtocoloSessao> protocoloCriado;

    @OneToMany(mappedBy = "finalizadoPor")
    private List<ProtocoloSessao> protocolofinalizado;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL
    )
    private List<HistoricoSalvamento> historicoSalvamentos;

}
