package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;

public interface ProfessorService {
    public ProfessorResponseDto cadastrarProfessor(ProfessorRequestDto dto);

}
