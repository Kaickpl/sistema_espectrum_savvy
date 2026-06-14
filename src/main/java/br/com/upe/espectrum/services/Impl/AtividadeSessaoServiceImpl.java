package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Pontuacao;
import br.com.upe.espectrum.repositories.AtividadeSessaoRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.AtividadeSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtividadeSessaoServiceImpl implements AtividadeSessaoService {

    @Autowired
    private AtividadeSessaoRepository atividadeSessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public AtividadeSessao atualizarPontuacao(UUID atividadeId, UUID usuarioId, Pontuacao pontuacao) {
        Optional<AtividadeSessao> atividadeExistente = atividadeSessaoRepository.findById(atividadeId);
        if (atividadeExistente.isEmpty()) {
            System.out.println("NENHUMA Atividade ENCONTRADA");
            return null;
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioId);
        if (usuarioExistente.isEmpty()) {
            System.out.println("NENHUM Usuario ENCONTRADO");
            return null;
        }

        AtividadeSessao atividade = atividadeExistente.get();
        atividade.setPontuacao(pontuacao);
        atividade.setAtualizadoPor(usuarioExistente.get());

        return atividadeSessaoRepository.save(atividade);
    }

    @Override
    public AtividadeSessao buscarAtividade(UUID atividadeId) {
        Optional<AtividadeSessao> atividade = atividadeSessaoRepository.findById(atividadeId);
        if (atividade.isEmpty()) {
            return null;
        }
        return atividade.get();
    }

    @Override
    public List<AtividadeSessao> buscarTodasPorCategoria(UUID categoriaSessaoId) {
        return atividadeSessaoRepository.findAllByCategoriaSessaoId(categoriaSessaoId);

    }
}
