package br.com.upe.espectrum.controllers;
import br.com.upe.espectrum.dto.mappers.AdminMapper;
import br.com.upe.espectrum.dto.mappers.TerapeutaMapper;
import br.com.upe.espectrum.dto.responseDtos.AdminDashboardResponseDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.services.AdminService;
import br.com.upe.espectrum.services.TerapeutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final TerapeutaService terapeutaService;
    private final AdminMapper adminMapper;
    private final TerapeutaMapper terapeutaMapper;

    @GetMapping("{idAdmin}")
    public ResponseEntity<AdminResponseDto> buscarAdmin(@PathVariable UUID idAdmin){
        AdminResponseDto response = adminService.buscarAdmin(idAdmin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponseDto> buscarDashboard(){
        AdminDashboardResponseDto response = adminService.buscarDashboardAdmin();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/terapeuta/{idTerapeuta}")
    public ResponseEntity<TerapeutaResponseDto> aprovarCadastroTerapeuta(@PathVariable UUID idTerapeuta){
        TerapeutaResponseDto terapeutaAtualizado = terapeutaService.aprovarCadastroTerapeuta(idTerapeuta);
        return ResponseEntity.ok(terapeutaAtualizado);
    }

    @DeleteMapping("{idAdmin}")
    public ResponseEntity<Void> inativarAdmin(@PathVariable UUID idAdmin){
        adminService.desativarContaAdmin(idAdmin);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{idAdmin}/reativar")
    public ResponseEntity<AdminResponseDto> reativarAdmin(@PathVariable UUID idAdmin){
        adminService.reativarContaAdmin(idAdmin);
        AdminResponseDto response = adminService.buscarAdmin(idAdmin);
        return ResponseEntity.ok(response);
    }
}
