package br.com.upe.espectrum.repositories.protocolo;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProtocoloSessaoRepository extends JpaRepository<ProtocoloSessao, UUID> {
    List<ProtocoloSessao> findAllByPacienteId(UUID pacienteId);

    long countByPaciente_Admin_IdAndStatusProtocolo(UUID adminId, StatusProtocolo statusProtocolo);
}
