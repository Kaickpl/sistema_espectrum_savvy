package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.CategoriaTemplete;
import br.com.upe.espectrum.dtos.RequestDtos.CategoriaTempleteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaTempleteService {
    CategoriaTemplete criarCategoria(CategoriaTempleteDto categoriaTempleteDto);
    List<CategoriaTemplete> BuscarTodos();


}
