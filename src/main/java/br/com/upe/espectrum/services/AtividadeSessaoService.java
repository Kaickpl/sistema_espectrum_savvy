package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.enums.Pontuacao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface AtividadeSessaoService {
    AtividadeSessao atualizarPontuacao(UUID atividadeId, UUID usuarioId, Pontuacao pontuacao);

    // Busca atividade por ID
    AtividadeSessao buscarAtividade(UUID atividadeId);

    // Lista todas as atividades de uma categoria
    List<AtividadeSessao> buscarTodasPorCategoria(UUID categoriaSessaoId);
}
