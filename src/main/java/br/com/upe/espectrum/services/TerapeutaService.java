package br.com.upe.espectrum.services;
import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;

import java.util.List;
import java.util.UUID;

public interface TerapeutaService {
    public TerapeutaResponseDto cadastrarTerapeuta(TerapeutaRequestDto dto);
    public TerapeutaResponseDto buscarTerapeuta(UUID idTerapeuta);
    public TerapeutaResponseDto aprovarCadastroTerapeuta(UUID idTerapeuta);
    public List<TerapeutaResponseDto> buscarTerapeutasPorAdm(UUID idAdmin);
    public List<TerapeutaResponseDto> buscarTerapeutasPendentesPorAdmin(UUID idAdmin);
    public TerapeutaResponseDto reativarContaTerapeuta(UUID idTerapeuta);
    public void desativarContaTerapeuta(UUID id);
}
