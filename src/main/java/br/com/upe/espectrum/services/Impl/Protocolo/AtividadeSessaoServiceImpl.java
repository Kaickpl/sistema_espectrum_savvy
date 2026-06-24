package br.com.upe.espectrum.services.Impl.Protocolo;

import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Pontuacao;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.repositories.protocolo.AtividadeSessaoRepository;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.paciente.PacienteService;
import br.com.upe.espectrum.services.protocolo.AtividadeSessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtividadeSessaoServiceImpl implements AtividadeSessaoService {

    @Autowired
    private AtividadeSessaoRepository atividadeSessaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteService pacienteService;

    @Override
    public AtividadeSessao atualizarPontuacao(UUID atividadeId, UUID usuarioId, Pontuacao pontuacao) {

        AtividadeSessao atividadeSessao = this.buscarAtividade(atividadeId);
        Paciente paciente = atividadeSessao.getCategoriaSessao().getProtocoloSessao().getPaciente();

        if(!pacienteService.verificarSePacientePertenceAoUsuario(paciente.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar ou alterar o protocolo este paciente.");
        }

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
