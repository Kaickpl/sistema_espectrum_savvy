package br.com.upe.espectrum.dtos.ResponseDtos;

import br.com.upe.espectrum.entities.Paciente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacienteResponseDTO {
    private String nome;
    private LocalDate dataNascimento;
    private String genero;
    private String cpf;
    private UUID terapeutaId;
    private UUID responsavelId;
    private UUID adminId;
    private int grau;
    public PacienteResponseDTO(Paciente paciente){
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.genero = paciente.getGenero();
        this.cpf = paciente.getCpf();
        this.terapeutaId = paciente.getTerapeuta().getId();
        this.adminId = paciente.getAdmin().getId();
        if (paciente.getResponsavel() != null){
            this.responsavelId = paciente.getResponsavel().getId();
        }

        this.grau = paciente.getGrau();
    }
}
