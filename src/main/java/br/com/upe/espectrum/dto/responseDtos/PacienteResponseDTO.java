package br.com.upe.espectrum.dto.responseDtos;

import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.VinculoEscolar;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import br.com.upe.espectrum.entities.enums.GrauAutismo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
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
    private UUID adminId;
    private GrauAutismo grau;
    private List<VinculoTerapeutaResponseDto> vinculosTerapeuta;
    private List<VinculoEscolarResponseDto> vinculosProfessor;

}
