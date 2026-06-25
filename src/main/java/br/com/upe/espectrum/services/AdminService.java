package br.com.upe.espectrum.services;

import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminDashboardResponseDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AdminService {
    public AdminResponseDto cadastrarAdmin(AdminRequestDto dto);
    public Admin buscarAdminEntityPorCodigo(String codigoConvite);
    public AdminResponseDto buscarAdmin(UUID idAdmin);
    public Admin buscarAdminEntity(UUID idAdmin);
    public AdminResponseDto reativarContaAdmin(UUID idAdmin);
    public void desativarContaAdmin(UUID id);
    public AdminDashboardResponseDto buscarDashboardAdmin();
}
