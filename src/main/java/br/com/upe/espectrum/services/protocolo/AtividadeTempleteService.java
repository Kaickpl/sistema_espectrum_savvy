package br.com.upe.espectrum.services.protocolo;
import br.com.upe.espectrum.dto.requestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AtividadeTempleteService {
    AtividadeTemplete criarAtividade(AtividadetempleteDto atividadeDto, UUID categoriaId);
}
