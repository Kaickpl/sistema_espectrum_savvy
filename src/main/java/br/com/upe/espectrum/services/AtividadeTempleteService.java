package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dtos.RequestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AtividadeTempleteService {
    AtividadeTemplete criarAtividade(AtividadetempleteDto atividadeDto, UUID categoriaId);
}
