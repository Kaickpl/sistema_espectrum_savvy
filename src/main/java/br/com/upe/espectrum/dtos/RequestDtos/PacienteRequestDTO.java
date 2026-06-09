package br.com.upe.espectrum.dtos.RequestDtos;

import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Responsavel;
import br.com.upe.espectrum.entities.Terapeuta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PacienteRequestDTO {
    private String nome;
    private LocalDate dataNascimento;
    private String genero;
    private String cpf;
    private UUID adminId;
    private UUID responsavelId;
    private UUID terapeutaId;
    private int grau;
    public PacienteRequestDTO(Paciente paciente){
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.genero = paciente.getGenero();
        this.cpf = paciente.getCpf();
        this.adminId = paciente.getAdmin().getId();
        this.responsavelId = paciente.getResponsavel().getId();
        this.terapeutaId = paciente.getTerapeuta().getId();
        this.grau = paciente.getGrau();
    }
}
