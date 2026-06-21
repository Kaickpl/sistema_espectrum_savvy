package br.com.upe.espectrum.services;
import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.enums.StatusCadastro;

import java.util.List;
import java.util.UUID;

public interface TerapeutaService {
    public TerapeutaResponseDto cadastrarTerapeuta(TerapeutaRequestDto dto, Admin admin, boolean isAtivo, StatusCadastro status);
    public TerapeutaResponseDto cadastroPeloTerapeuta(TerapeutaRequestDto dto);
    public TerapeutaResponseDto cadastroPeloAdmin(TerapeutaRequestDto dto);
    public TerapeutaResponseDto buscarTerapeuta(UUID idTerapeuta);
    public TerapeutaResponseDto aprovarCadastroTerapeuta(UUID idTerapeuta);
    public List<TerapeutaResponseDto> buscarTerapeutasPorAdm(UUID idAdmin);
    public List<TerapeutaResponseDto> buscarTerapeutasPendentesPorAdmin(UUID idAdmin);
    public TerapeutaResponseDto reativarContaTerapeuta(UUID idTerapeuta);
    public void desativarContaTerapeuta(UUID id);
}
