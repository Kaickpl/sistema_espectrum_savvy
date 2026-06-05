package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProtocoloSessaoService {

    ProtocoloSessao iniciarProtocoloSessao(UUID usuarioId, UUID pacienteId);

}
