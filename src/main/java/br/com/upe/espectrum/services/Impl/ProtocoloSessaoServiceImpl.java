package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.exceptions.OperacaoNaoPermitida;
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
            System.out.println("NENHUM Usuario ENCONTRADO");

            return null;
        }
        Optional<Paciente> pacienteExistente = pacienteRepository.findById(pacienteId);
        if(pacienteExistente.isEmpty()){
            System.out.println("NENHUM Paciente ENCONTRADO");

            return null;
        }
        Optional<ProtocoloTemplete> protocoloTemplete = protocoloTempleteRepository.findAll().stream().findFirst();
        if (protocoloTemplete.isEmpty()){
            System.out.println("NENHUM PROTOCOLO TEMPLATE ENCONTRADO");
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
    public ProtocoloSessao finalizarProtocoloSessao(UUID usuarioId, UUID sessaoId) {

        Optional<ProtocoloSessao> protocoloSessao = protocoloSessaoRepository.findById(sessaoId);
        if(protocoloSessao.isEmpty()){
        throw new InformacaoNaoEncontradoException("Protocolo com Id: " +  sessaoId + " não enconrado");
        }
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuarioId);
        if(usuarioExistente.isEmpty()){
            throw new InformacaoNaoEncontradoException("Usuario com id"  + usuarioId + " não encontrado! ");
        }

        if (!protocoloSessao.get().getCriadoPor().getId().equals(usuarioId)) {
            throw new OperacaoNaoPermitida(" Operação não permitida, usuario não autorizado a finalizar o protocolo. ");
        }

        protocoloSessao.get().setStatusProtocolo(StatusProtocolo.FINALIZADO);
        protocoloSessao.get().setDataFinal(LocalDateTime.now());
        protocoloSessao.get().setFinalizadoPor(usuarioExistente.get());
        return protocoloSessaoRepository.save(protocoloSessao.get());
    }

    @Override
    public ProtocoloSessao buscarProtocoloSessao(UUID sessaoId) {
        Optional<ProtocoloSessao> sessao =  protocoloSessaoRepository.findById(sessaoId);
        if (sessao.isEmpty()) {
           throw new  InformacaoNaoEncontradoException("Protocolo com Id: " + sessaoId + "não encontrado! ");
        }
        return sessao.get();
    }

    @Override
    public List<ProtocoloSessao> buscarTodosPorPaciente(UUID pacienteId) {
        if(pacienteId == null) {
            throw new InformacaoNaoEncontradoException(" Paciente com Id: " + pacienteId + " não encontrado! ");
        }
        return protocoloSessaoRepository.findAllByPacienteId(pacienteId);
    }

    @Override
    public ProtocoloSessao salvarProgresso(UUID sessaoId, UUID usuarioId) {

        Optional<ProtocoloSessao> sessao = protocoloSessaoRepository.findById(sessaoId);
        if (sessao.isEmpty()) {
            throw new  InformacaoNaoEncontradoException("Protocolo com Id: " + sessaoId + "não encontrado! ");

        }
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
        if (usuario.isEmpty()) {
            throw new InformacaoNaoEncontradoException("Usuario com id"  + usuarioId + " não encontrado! ");

        }
        HistoricoSalvamento historico = new HistoricoSalvamento();
        historico.setUsuario(usuario.get());
        historico.setDataSalvamento(LocalDateTime.now());
        historico.setProtocoloSessao(sessao.get());
        sessao.get().getHistoricoSalvamentos().add(historico);
        return protocoloSessaoRepository.save(sessao.get());
    }


}
