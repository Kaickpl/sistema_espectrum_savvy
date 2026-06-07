package br.com.upe.espectrum.controller;

import br.com.upe.espectrum.dtos.ResponseDtos.ProtocoloTempleteResponseDto;
import br.com.upe.espectrum.entities.ProtocoloTemplete;
import br.com.upe.espectrum.services.ProtocoloTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/protocolo")
public class ProtocoloTempleteController {

    @Autowired
    private ProtocoloTempleteService protocoloTempleteService;

    @PostMapping
    public ResponseEntity<ProtocoloTempleteResponseDto>CriarProtocolo(){
        ProtocoloTemplete protocoloTemplete =  protocoloTempleteService.criarProtocolo();
        ProtocoloTempleteResponseDto dto = new ProtocoloTempleteResponseDto(protocoloTemplete);
        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<ProtocoloTempleteResponseDto> BuscarProtocolo(){
        ProtocoloTemplete protocoloTemplete =  protocoloTempleteService.BuscarProtocolo();
        return ResponseEntity.ok(new ProtocoloTempleteResponseDto(protocoloTemplete));

    }

}
