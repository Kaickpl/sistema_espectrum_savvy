package br.com.upe.espectrum.repositories;

import br.com.upe.espectrum.entities.Terapeuta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TerapeutaRepository extends JpaRepository <Terapeuta, UUID> {
}
