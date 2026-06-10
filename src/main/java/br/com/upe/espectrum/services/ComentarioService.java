package br.com.upe.espectrum.services;

import br.com.upe.espectrum.entities.Comentario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public interface ComentarioService {

    Comentario adicionarComentarioProtocolo(UUID sessaoId, UUID usuarioId, String texto);

    Comentario adicionarComentarioCategoria(UUID categoriaSessaoId, UUID usuarioId, String texto);

    List<Comentario> buscarComentariosProtocolo(UUID sessaoId);

    List<Comentario> buscarComentariosCategoria(UUID categoriaSessaoId);
}
