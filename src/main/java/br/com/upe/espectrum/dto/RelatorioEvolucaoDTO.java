package br.com.upe.espectrum.dto;

import br.com.upe.espectrum.entities.enums.GrauAutismo;

import java.util.List;

public record RelatorioEvolucaoDTO (
        String pacienteNome,
        int pacienteIdade,
        GrauAutismo pacienteGrau,
        String terapeutaNome,
        List<PontoGraficoLinhaDTO> historicoAtencaoCompartilhada,
        List<PontoGraficoLinhaDTO> historicoBrincarSocial,
        List<PontoGraficoLinhaDTO> historicoAutoRegulacao,
        List<PontoGraficoLinhaDTO> historicoSocialEmocional,
        List<PontoGraficoLinhaDTO> historicoLinguagemSocial,
        List<PontoGraficoLinhaDTO> historicoComportamentos,
        List<PontoGraficoLinhaDTO> historicoLinguagemSocialNaoVerbal,
        List<PontuacaoCategoriaDTO> comparativoAtual,
        List<ObservacaoRelatorioDTO> observacoes
){
}

