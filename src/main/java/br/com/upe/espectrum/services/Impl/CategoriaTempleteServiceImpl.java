package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.dto.requestDtos.CategoriaTempleteDto;
import br.com.upe.espectrum.entities.ProtocoloTemplete;
import br.com.upe.espectrum.repositories.CategoriaTempleteRepository;
import br.com.upe.espectrum.repositories.ProtocoloTempleteRepository;
import br.com.upe.espectrum.services.CategoriaTempleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTempleteServiceImpl implements CategoriaTempleteService {

    @Autowired
    private CategoriaTempleteRepository categoriaTempleteRepository;

    @Autowired
    private ProtocoloTempleteRepository protocoloTempleteRepository;

    @Override
    public CategoriaTemplete criarCategoria(CategoriaTempleteDto categoriaTempleteDto) {
        if (categoriaTempleteDto.getNomeCategoria() == null || categoriaTempleteDto.getNomeCategoria().isBlank() ) {
            return null;
        }
        if (categoriaTempleteDto.getDescricaoCategoria()== null || categoriaTempleteDto.getDescricaoCategoria().isBlank() ) {
        return null;}
        CategoriaTemplete categoriaExiste = categoriaTempleteRepository.findByNomeCategoriaIgnoreCase(categoriaTempleteDto.getNomeCategoria());
        if (categoriaExiste != null) {
            return null;
        }
        ProtocoloTemplete protocolo = protocoloTempleteRepository.findAll().stream().findFirst().orElse(null);
        CategoriaTemplete categoria = new CategoriaTemplete();
        categoria.setNomeCategoria(categoriaTempleteDto.getNomeCategoria());
        categoria.setDescricaoCategoria(categoriaTempleteDto.getDescricaoCategoria());
        categoria.setProtocolo(protocolo);
        return categoriaTempleteRepository.save(categoria);
    }

    @Override
    public List<CategoriaTemplete> BuscarTodos() {
        return categoriaTempleteRepository.findAll();
    }


}
