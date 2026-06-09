package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ProtocoloSessaoRepository;
import br.com.upe.espectrum.repositories.ProtocoloTempleteRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.ProtocoloSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProtocoloSessaoServiceImpl implements ProtocoloSessaoService {

    @Autowired
    private ProtocoloSessaoRepository protocoloSessaoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProtocoloTempleteRepository protocoloTempleteRepository;


    @Override
    public ProtocoloSessao iniciarProtocoloSessao(UUID usuarioId, UUID pacienteId) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioId);
        if(usuarioExistente.isEmpty()){
            return null;
        }
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(pacienteId);
        if(pacienteExistente.isEmpty()){
            return null;
        }
        Optional<ProtocoloTemplete> protocoloTemplete = protocoloTempleteRepository.findAll().stream().findFirst();
        if (protocoloTemplete.isEmpty()){
            return null;
        }
        Usuario usuario = usuarioExistente.get();
        Paciente paciente = pacienteExistente.get();
        ProtocoloTemplete protocoloTemplete1 = protocoloTemplete.get();

        ProtocoloSessao sessaoProtocolo = new ProtocoloSessao();

        sessaoProtocolo.setDataInicio(LocalDateTime.now());
        sessaoProtocolo.setStatusProtocolo(StatusProtocolo.EM_ANDAMENTO);
        sessaoProtocolo.setCriadoPor(usuario);
        sessaoProtocolo.setPaciente(paciente);
        sessaoProtocolo.setProtocoloTemplete(protocoloTemplete1);

        List<CategoriaSessao> categorias = protocoloTemplete1.getCategorias().stream()
                .map(categoriaTemplete -> {

                    CategoriaSessao categoriaSessao = new CategoriaSessao();
                    categoriaSessao.setCategoriaTemplete(categoriaTemplete);
                    categoriaSessao.setProtocoloSessao(sessaoProtocolo);

                    // Copia AtividadeTemplete → AtividadeSessao
                    List<AtividadeSessao> atividades = categoriaTemplete.getAtividades().stream()
                            .map(atividadeTemplete -> {
                                AtividadeSessao atividadeSessao = new AtividadeSessao();
                                atividadeSessao.setAtividadeTemplete(atividadeTemplete);
                                atividadeSessao.setCategoriaSessao(categoriaSessao);
                                return atividadeSessao;
                            }).toList();

                    categoriaSessao.setAtividadeSessao(atividades);
                    return categoriaSessao;
                }).toList();
        sessaoProtocolo.setCategoriasSessao(categorias);
        return protocoloSessaoRepository.save(sessaoProtocolo);
    }

    @Override
    public ProtocoloSessao FinalizarProtocoloSessao(UUID usuarioId, UUID pacienteId) {
        return null;
    }

    @Override
    public ProtocoloSessao buscarProtocoloSessao(UUID sessaoId) {
        return null;
    }

    @Override
    public List<ProtocoloSessao> buscarTodosPorPaciente(UUID pacienteId) {
        return List.of();
    }

    @Override
    public ProtocoloSessao salvarProgresso(UUID sessaoId, UUID usuarioId) {
        return null;
    }


}
