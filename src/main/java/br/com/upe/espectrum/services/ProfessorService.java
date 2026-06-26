package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResumoResponseDto;

import java.util.List;

public interface ProfessorService {
    public ProfessorResponseDto cadastrarProfessor(ProfessorRequestDto dto);

    public List<ProfessorResumoResponseDto> listarProfessores();

}
