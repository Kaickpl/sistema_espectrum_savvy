package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.ProtocoloSessao;
import br.com.upe.espectrum.entities.enums.StatusProtocolo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProtocoloSessaoResponseDto {
    private UUID id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private StatusProtocolo statusProtocolo;
    private UUID pacienteId;
    private UUID criadoPorId;
    private String criadoPorNome;
    private UUID finalizadoPorId;
    private List<CategoriaSessaoResponseDto> categoriasSessao;
    public ProtocoloSessaoResponseDto(ProtocoloSessao sessao) {
        this.id = sessao.getId();
        this.dataInicio = sessao.getDataInicio();
        this.dataFinal = sessao.getDataFinal();
        this.statusProtocolo = sessao.getStatusProtocolo();
        this.pacienteId = sessao.getPaciente().getId();
        this.criadoPorId = sessao.getCriadoPor().getId();
        this.criadoPorNome = sessao.getCriadoPor().getNome();
        this.finalizadoPorId = sessao.getFinalizadoPor() != null
                ? sessao.getFinalizadoPor().getId()
                : null;
        this.categoriasSessao = sessao.getCategoriasSessao()
                .stream()
                .map(CategoriaSessaoResponseDto::new)
                .toList();
    }

}
