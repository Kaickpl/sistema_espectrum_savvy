package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.CategoriaSessao;
import br.com.upe.espectrum.entities.Comentario;
import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.repositories.CategoriaSessaoRepository;
import br.com.upe.espectrum.repositories.ComentarioRepository;
import br.com.upe.espectrum.repositories.ProtocoloSessaoRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioSeviceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ProtocoloSessaoRepository protocoloSessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaSessaoRepository categoriaSessaoRepository;



    @Override
    public Comentario adicionarComentarioProtocolo(UUID sessaoId, UUID usuarioId, String texto) {
        Optional<ProtocoloSessao> sessao = protocoloSessaoRepository.findById(sessaoId);
        if (sessao.isEmpty()) {
            return null;
        }
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            return null;
        }

        Comentario comentario = new Comentario();
        if(texto == null || texto.isBlank()){
            return null;
        }
        comentario.setUsuario(usuario.get());
        comentario.setComentario(texto);
        comentario.setProtocoloSessao(sessao.get());
        return comentarioRepository.save(comentario);
    }

    @Override
    public Comentario adicionarComentarioCategoria(UUID categoriaSessaoId, UUID usuarioId, String texto) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new InformacaoNaoEncontradoException("Usuario com Id: " + usuarioId + " não encontrado");
        }
        Optional<CategoriaSessao> categoria = categoriaSessaoRepository.findById(categoriaSessaoId);
        if (categoria.isEmpty()) {
            throw new InformacaoNaoEncontradoException("Usuario com Id: " + categoriaSessaoId + " não encontrado");

        }
        Comentario comentario = new Comentario();
        if(texto == null || texto.isBlank()){
            throw new CampoObrigatorioException(" Campo comentario não pode ser vazio");
        }
        comentario.setUsuario(usuario.get());
        comentario.setCategoriaSessao(categoria.get());
        comentario.setComentario(texto);
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> buscarComentariosProtocolo(UUID sessaoId) {
        if(sessaoId == null) {
            throw new InformacaoNaoEncontradoException();
        }
        return comentarioRepository.findAllByProtocoloSessaoId(sessaoId);
    }

    @Override
    public List<Comentario> buscarComentariosCategoria(UUID categoriaSessaoId) {
        return comentarioRepository.findAllByCategoriaSessaoId(categoriaSessaoId);
    }
}
