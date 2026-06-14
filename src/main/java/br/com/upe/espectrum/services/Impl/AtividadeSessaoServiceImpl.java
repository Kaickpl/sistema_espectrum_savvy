package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Pontuacao;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
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
            throw new InformacaoNaoEncontradoException("Atividade não encontrada!");

        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioId);
        if (usuarioExistente.isEmpty()) {
            throw new InformacaoNaoEncontradoException("Usuario com id"  + usuarioId + " não encontrado! ");
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
            throw new InformacaoNaoEncontradoException("Atividade com Id" + atividadeId + "não encontrada!");
        }
        return atividade.get();
    }

    @Override
    public List<AtividadeSessao> buscarTodasPorCategoria(UUID categoriaSessaoId) {
        if (categoriaSessaoId == null) {
            throw new InformacaoNaoEncontradoException("Atividade com Id" + categoriaSessaoId + "não encontrada!");
        }
        return atividadeSessaoRepository.findAllByCategoriaSessaoId(categoriaSessaoId);

    }
}
