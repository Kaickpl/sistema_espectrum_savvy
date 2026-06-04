package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.DTOs.ProtocoloTempleteDto;
import br.com.upe.espectrum.entities.ProtocoloTemplete;

import java.util.UUID;

public interface ProtocoloTempleteService {

    public ProtocoloTemplete criarProtocolo();
    public  ProtocoloTemplete BuscarProtocolo();


}
