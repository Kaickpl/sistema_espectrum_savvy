package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dto.responseDtos.ObservacaoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.PontoEvolucaoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.PontuacaoRecenteResponseDto;
import br.com.upe.espectrum.dto.responseDtos.RelatorioEvolucaoResponseDto;
import br.com.upe.espectrum.entities.AtividadeSessao;
import br.com.upe.espectrum.entities.CategoriaSessao;
import br.com.upe.espectrum.entities.Comentario;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.entities.enums.Pontuacao;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.protocolo.ProtocoloSessaoRepository;
import br.com.upe.espectrum.services.RelatorioService;
import br.com.upe.espectrum.services.paciente.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    @Autowired
    private ProtocoloSessaoRepository protocoloSessaoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Override
    @Transactional
    public RelatorioEvolucaoResponseDto gerarRelatorioEvolucao(UUID pacienteId, int meses) {

        if (!pacienteService.verificarSePacientePertenceAoUsuario(pacienteId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar o relatório deste paciente.");
        }

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new InformacaoNaoEncontradoException("Paciente com Id: " + pacienteId + " não encontrado!"));

        List<ProtocoloSessao> sessoes = meses > 0
                ? protocoloSessaoRepository.findSessoesPorPacienteEData(pacienteId, LocalDateTime.now().minusMonths(meses))
                : protocoloSessaoRepository.findAllByPacienteIdOrderByDataInicioAsc(pacienteId);

        List<PontoEvolucaoResponseDto> evolucaoTemporal = new ArrayList<>();
        List<ObservacaoResponseDto> observacoes = new ArrayList<>();
        List<PontuacaoRecenteResponseDto> ultimasPontuacoes = new ArrayList<>();
        Map<String, List<Double>> pontuacoesPorCategoria = new LinkedHashMap<>();

        int numeroSessao = 0;
        for (ProtocoloSessao sessao : sessoes) {
            numeroSessao++;

            Map<String, Double> mediaPorCategoriaSessao = new LinkedHashMap<>();
            List<Double> todasPontuacoesSessao = new ArrayList<>();

            for (CategoriaSessao categoriaSessao : sessao.getCategoriasSessao()) {
                String nomeCategoria = categoriaSessao.getCategoriaTemplete().getNomeCategoria();

                List<Double> valoresCategoria = categoriaSessao.getAtividadeSessao().stream()
                        .filter(a -> a.getPontuacao() != null && a.getPontuacao() != Pontuacao.NAO_APLICAVEL)
                        .map(a -> (double) a.getPontuacao().getValor())
                        .toList();

                if (!valoresCategoria.isEmpty()) {
                    double mediaCategoria = valoresCategoria.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                    mediaPorCategoriaSessao.put(nomeCategoria, mediaCategoria);
                    todasPontuacoesSessao.addAll(valoresCategoria);
                    pontuacoesPorCategoria.computeIfAbsent(nomeCategoria, k -> new ArrayList<>()).addAll(valoresCategoria);
                }

                for (Comentario comentario : categoriaSessao.getComentarios()) {
                    observacoes.add(new ObservacaoResponseDto(comentario, nomeCategoria, numeroSessao));
                }

                for (AtividadeSessao atividade : categoriaSessao.getAtividadeSessao()) {
                    if (atividade.getPontuacao() != null) {
                        ultimasPontuacoes.add(new PontuacaoRecenteResponseDto(
                                atividade.getAtividadeTemplete().getNomeAtividade(),
                                nomeCategoria,
                                numeroSessao,
                                atividade.getAtualizadoEm(),
                                atividade.getPontuacao().getValor()
                        ));
                    }
                }
            }

            for (Comentario comentario : sessao.getComentarios()) {
                observacoes.add(new ObservacaoResponseDto(comentario, null, numeroSessao));
            }

            if (!todasPontuacoesSessao.isEmpty()) {
                double mediaGeral = todasPontuacoesSessao.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                evolucaoTemporal.add(new PontoEvolucaoResponseDto(
                        sessao.getId(), numeroSessao, sessao.getDataInicio(), mediaGeral, mediaPorCategoriaSessao));
            }
        }

        Map<String, Double> comparativoCategorias = new LinkedHashMap<>();
        pontuacoesPorCategoria.forEach((categoria, valores) ->
                comparativoCategorias.put(categoria, valores.stream().mapToDouble(Double::doubleValue).average().orElse(0)));

        observacoes.sort(Comparator.comparing(ObservacaoResponseDto::dataCriacao, Comparator.nullsLast(Comparator.reverseOrder())));
        ultimasPontuacoes.sort(Comparator.comparing(PontuacaoRecenteResponseDto::data, Comparator.nullsLast(Comparator.reverseOrder())));

        String nomeTerapeuta = paciente.getEquipeMultiDisciplinar().stream()
                .findFirst()
                .map(vinculo -> vinculo.getUsuario().getNome())
                .orElse("Não atribuído");

        Integer idade = paciente.getDataNascimento() != null
                ? Period.between(paciente.getDataNascimento(), LocalDate.now()).getYears()
                : null;

        Integer nivelSuporte = paciente.getGrauAutismo() != null
                ? paciente.getGrauAutismo().ordinal() + 1
                : null;

        return new RelatorioEvolucaoResponseDto(
                paciente.getId(),
                paciente.getNome(),
                idade,
                nivelSuporte,
                nomeTerapeuta,
                new ArrayList<>(pontuacoesPorCategoria.keySet()),
                evolucaoTemporal,
                comparativoCategorias,
                observacoes.size() > 10 ? observacoes.subList(0, 10) : observacoes,
                ultimasPontuacoes.size() > 10 ? ultimasPontuacoes.subList(0, 10) : ultimasPontuacoes
        );
    }
}
