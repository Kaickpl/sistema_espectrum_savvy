package br.com.upe.espectrum.entities;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.entities.enums.StatusCadastro;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "telefone", unique = true, nullable = false)
    private String numeroTelefone;

    @Column(name = "email", unique = true)
    private String email;

    private String senha;

    @Column(name = "cpf", unique = true, nullable = false)
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
    private List<VinculoTerapeuta> vinculoTerapeutas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<VinculoEscolar> vinculosEscolares = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<VinculoResponsavel> vinculosResponsaveis = new ArrayList<>();

    @OneToMany(mappedBy = "criadoPor")
    private List<ProtocoloSessao> protocoloCriados;

    @OneToMany(mappedBy = "finalizadoPor")
    private List<ProtocoloSessao> protocolofinalizados;

    @OneToMany(mappedBy = "atualizadoPor")
    private List<AtividadeSessao> atividadesAtualizadas;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL
    )
    private List<HistoricoSalvamento> historicoSalvamentos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.tipo.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email != null ? this.email : this.numeroTelefone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (!this.isActive) {
            return false;
        }
        if (this.perfilTerapeuta != null && this.perfilTerapeuta.getStatusCadastro() != StatusCadastro.APROVADO) {
            return false;
        }
        return true;
    }
}
