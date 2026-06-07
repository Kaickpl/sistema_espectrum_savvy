package br.com.upe.espectrum.dto.responseDtos;
import java.util.List;
import java.util.UUID;

public record ResponsavelResponseDto(
        UUID idResponsavel,
        String numeroTelefone,
        String email,
        String nome,
        String grauParentesco,
        List<PacienteResumoResponseDto> pacientesTutelados
) {
}
