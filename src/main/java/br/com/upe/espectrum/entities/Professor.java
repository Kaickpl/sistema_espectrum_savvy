package br.com.upe.espectrum.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vinculo_escolar SET is_active = false WHERE id = ?")
@SQLRestriction("is_active = true")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String escola;

    @OneToOne
    private Usuario usuario;

    @Column(name = "is_active")
    private boolean isActive = true;

}
