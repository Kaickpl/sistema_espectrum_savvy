package br.com.upe.espectrum.dto.requestDtos;
import br.com.upe.espectrum.entities.Paciente;
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
    private String grauAutismo;
    private UUID adminId;

    public PacienteRequestDTO(Paciente paciente){
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.genero = paciente.getGenero();
        this.cpf = paciente.getCpf();
        this.adminId = paciente.getAdmin().getId();
        this.grauAutismo = paciente.getGrauAtismo();
    }
}
