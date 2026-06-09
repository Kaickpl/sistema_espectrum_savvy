package br.com.upe.espectrum.controllers;
import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.requestDtos.ResponsavelAvulsoRequestDto;
import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.services.Impl.AdminCadastroServiceImpl;
import br.com.upe.espectrum.services.Impl.ProfessorCadastroServiceImpl;
import br.com.upe.espectrum.services.Impl.ResponsavelCadastroServiceImpl;
import br.com.upe.espectrum.services.Impl.TerapeutaCadastroServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cadastro")
public class CadastroController {

    private final AdminCadastroServiceImpl adminService;
    private final TerapeutaCadastroServiceImpl terapeutaService;
    private final ProfessorCadastroServiceImpl professorService;
    private final ResponsavelCadastroServiceImpl responsavelService;

    @PostMapping("/admin")
    public ResponseEntity<AdminResponseDto> cadastrarAdmin(@RequestBody AdminRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.cadastrarAdmin(dto));
    }

    @PostMapping("/terapeuta")
    public ResponseEntity<TerapeutaResponseDto> cadastrarTerapeuta(@RequestBody TerapeutaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(terapeutaService.cadastrarTerapeuta(dto));
    }

    @PostMapping("/professor")
    public ResponseEntity<ProfessorResponseDto> cadastrarProfessor(@RequestBody ProfessorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.cadastrarProfessor(dto));
    }

    @PostMapping("/responsavel")
    public ResponseEntity<ResponsavelResponseDto> cadastrarResponsavel(@RequestBody ResponsavelAvulsoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.cadastrarResponsavel(dto));
    }
}