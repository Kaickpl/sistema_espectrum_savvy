package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ProtocoloSessaoRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.ProtocoloSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProtocoloSessaoServiceImpl implements ProtocoloSessaoService {

    @Autowired
    private ProtocoloSessaoRepository protocoloSessaoRepositoryRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public ProtocoloSessao iniciarProtocoloSessao(UUID usuarioId, UUID pacienteId) {
        return null;
    }
}
