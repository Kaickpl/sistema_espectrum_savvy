package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProtocoloSessaoService {

    ProtocoloSessao iniciarProtocoloSessao(UUID usuarioId, UUID pacienteId);
    ProtocoloSessao FinalizarProtocoloSessao(UUID usuarioId, UUID pacienteId);
    ProtocoloSessao buscarProtocoloSessao(UUID sessaoId);
    List<ProtocoloSessao> buscarTodosPorPaciente(UUID pacienteId);
    ProtocoloSessao salvarProgresso(UUID sessaoId, UUID usuarioId);



}
