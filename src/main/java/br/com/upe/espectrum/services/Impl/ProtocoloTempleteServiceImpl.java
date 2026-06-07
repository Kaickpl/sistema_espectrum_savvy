package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.ProtocoloTemplete;
import br.com.upe.espectrum.repositories.ProtocoloTempleteRepository;
import br.com.upe.espectrum.services.ProtocoloTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocoloTempleteServiceImpl implements ProtocoloTempleteService {

    @Autowired
     ProtocoloTempleteRepository protocoloTempleteRepository;


    @Override
    public ProtocoloTemplete criarProtocolo() {
        ProtocoloTemplete protocolo = new ProtocoloTemplete();
        return protocoloTempleteRepository.save(protocolo);
    }

    @Override
    public ProtocoloTemplete BuscarProtocolo() {
        return protocoloTempleteRepository.findAll().stream().findFirst().orElseThrow(()-> new IllegalArgumentException("Nenhum protocolo encontrado") );
    }
}
