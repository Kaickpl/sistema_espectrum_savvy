package br.com.upe.espectrum.services.Impl.Protocolo;

import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.exceptions.OperacaoNaoPermitida;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.protocolo.ProtocoloSessaoRepository;
import br.com.upe.espectrum.repositories.protocolo.ProtocoloTempleteRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.paciente.PacienteService;
import br.com.upe.espectrum.services.protocolo.ProtocoloSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private PacienteService pacienteService;


    @Override
    @Transactional
    public ProtocoloSessao iniciarProtocoloSessao(UUID usuarioId, UUID pacienteId) {

        if (!pacienteService.verificarSePacientePertenceAoUsuario(pacienteId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este paciente.");
        }

        // 1. Tentar buscar um protocolo já existente para este paciente que esteja EM_ANDAMENTO
        Optional<ProtocoloSessao> sessaoExistente = protocoloSessaoRepository.findAllByPacienteId(pacienteId)
                .stream()
                .filter(s -> s.getStatusProtocolo() == StatusProtocolo.EM_ANDAMENTO)
                .findFirst();

        if (sessaoExistente.isPresent()) {
            return sessaoExistente.get();
        }

        // 2. Se não existir, prossegue com a lógica de criação que você já possui
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Usuario não encontrado"));

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Paciente não encontrado"));

        ProtocoloTemplete template = protocoloTempleteRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Nenhum template encontrado"));

        ProtocoloSessao sessaoProtocolo = new ProtocoloSessao();
        sessaoProtocolo.setDataInicio(LocalDateTime.now());
        sessaoProtocolo.setStatusProtocolo(StatusProtocolo.EM_ANDAMENTO);
        sessaoProtocolo.setCriadoPor(usuario);
        sessaoProtocolo.setPaciente(paciente);
        sessaoProtocolo.setProtocoloTemplete(template);

        List<CategoriaSessao> categorias = template.getCategorias().stream()
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
    @Transactional
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
    @Transactional

    public ProtocoloSessao buscarProtocoloSessao(UUID sessaoId) {
        ProtocoloSessao sessao = protocoloSessaoRepository.findByIdWithCategorias(sessaoId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Protocolo com Id: " + sessaoId + " não encontrado!"));

        if (!pacienteService.verificarSePacientePertenceAoUsuario(sessao.getPaciente().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para visualizar o histórico deste paciente.");
        }
        return sessao;
    }

    @Override
    @Transactional

    public List<ProtocoloSessao> buscarTodosPorPaciente(UUID pacienteId) {

        if (!pacienteService.verificarSePacientePertenceAoUsuario(pacienteId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar ou alterar este paciente.");
        }

        if(pacienteId == null) {
            throw new InformacaoNaoEncontradoException(" Paciente com Id: " + pacienteId + " não encontrado! ");
        }
        return protocoloSessaoRepository.findAllByPacienteId(pacienteId);
    }

    @Override
    @Transactional

    public ProtocoloSessao salvarProgresso(UUID sessaoId, UUID usuarioId) {

        ProtocoloSessao sessao = protocoloSessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Protocolo com Id: " + sessaoId + " não encontrado!"));

        if (!pacienteService.verificarSePacientePertenceAoUsuario(sessao.getPaciente().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para salvar progresso neste paciente.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Usuario com id " + usuarioId + " não encontrado!"));


        HistoricoSalvamento historico = new HistoricoSalvamento();
        historico.setUsuario(usuario);
        historico.setDataSalvamento(LocalDateTime.now());
        historico.setProtocoloSessao(sessao);

        sessao.getHistoricoSalvamentos().add(historico);
        return protocoloSessaoRepository.save(sessao);
    }
}
