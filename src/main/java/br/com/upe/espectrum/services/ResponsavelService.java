package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.PacienteEResponsavelRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;

public interface ResponsavelService {
    public ResponsavelResponseDto cadastrarPacienteEResponsavel(PacienteEResponsavelRequestDto dto);
}
