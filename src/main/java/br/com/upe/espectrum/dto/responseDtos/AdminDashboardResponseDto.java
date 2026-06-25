package br.com.upe.espectrum.dto.responseDtos;

public record AdminDashboardResponseDto(
        long totalPacientes,
        long totalProtocolosFinalizados
) {
}
