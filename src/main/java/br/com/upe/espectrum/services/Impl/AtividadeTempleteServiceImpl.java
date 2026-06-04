package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dtos.RequestDtos.AtividadetempleteDto;
import br.com.upe.espectrum.entities.AtividadeTemplete;
import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.repositories.AtividadeTempleteRepository;
import br.com.upe.espectrum.repositories.CategoriaTempleteRepository;
import br.com.upe.espectrum.services.AtividadeTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AtividadeTempleteServiceImpl implements AtividadeTempleteService {

    @Autowired
    private AtividadeTempleteRepository atividadeTempleteRepository;

    @Autowired
    private CategoriaTempleteRepository categoriaTempleteRepository;


    @Override
    public AtividadeTemplete criarAtividade(AtividadetempleteDto atividadeDto, UUID categoriaId) {
        if (atividadeDto.getNomeAtividade() == null
                || atividadeDto.getNomeAtividade().isBlank()) {
            return null;
        }

        AtividadeTemplete atividadeExiste =
                atividadeTempleteRepository
                        .findByNomeAtividadeIgnoreCase(
                                atividadeDto.getNomeAtividade());

        if (atividadeExiste != null) {
            return null;
        }
        CategoriaTemplete categoria = categoriaTempleteRepository.findById(categoriaId).orElse(null);

        if (categoria   == null) {
            return null;
        }

        AtividadeTemplete atividade =
                new AtividadeTemplete();

        atividade.setNomeAtividade(
                atividadeDto.getNomeAtividade());

        atividade.setCategoria(categoria);


        return atividadeTempleteRepository.save(atividade);

    }


}
