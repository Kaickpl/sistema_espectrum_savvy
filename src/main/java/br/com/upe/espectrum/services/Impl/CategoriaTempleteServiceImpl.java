package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.dto.requestDtos.CategoriaTempleteDto;
import br.com.upe.espectrum.entities.ProtocoloTemplete;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.exceptions.InformacaoExistenteException;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
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
            throw new CampoObrigatorioException(" Campo nome da categoria é obrigatório");
        }
        CategoriaTemplete categoriaExiste = categoriaTempleteRepository.findByNomeCategoriaIgnoreCase(categoriaTempleteDto.getNomeCategoria());
        if (categoriaExiste != null) {
            throw new InformacaoExistenteException("Categoria já cadastrda com esse nome" +  categoriaTempleteDto.getNomeCategoria());
        }
        ProtocoloTemplete protocolo = protocoloTempleteRepository.findAll().stream().findFirst().orElse(null);
        CategoriaTemplete categoria = new CategoriaTemplete();
        categoria.setNomeCategoria(categoriaTempleteDto.getNomeCategoria());
        categoria.setProtocolo(protocolo);
        return categoriaTempleteRepository.save(categoria);
    }

    @Override
    public List<CategoriaTemplete> BuscarTodos() {
        if (protocoloTempleteRepository.findAll().isEmpty()) {
            throw new InformacaoNaoEncontradoException();
        }
        return categoriaTempleteRepository.findAll();
    }


}
