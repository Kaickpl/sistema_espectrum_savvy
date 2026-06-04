package br.com.upe.espectrum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtocoloTemplete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocolo")
    private List<CategoriaTemplete> categorias = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "protocoloTemplete")
    private List<ProtocoloSessao> protocoloSessao = new ArrayList<>();

}
