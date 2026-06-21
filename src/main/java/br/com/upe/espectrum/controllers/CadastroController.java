package br.com.upe.espectrum.controllers;
import br.com.upe.espectrum.dto.requestDtos.*;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.services.AdminService;
import br.com.upe.espectrum.services.Impl.AdminServiceImpl;
import br.com.upe.espectrum.services.Impl.ProfessorServiceImpl;
import br.com.upe.espectrum.services.Impl.ResponsavelServiceImpl;
import br.com.upe.espectrum.services.Impl.TerapeutaServiceImpl;
import br.com.upe.espectrum.services.ProfessorService;
import br.com.upe.espectrum.services.ResponsavelService;
import br.com.upe.espectrum.services.TerapeutaService;
import jakarta.validation.Valid;
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

    private final AdminService adminService;
    private final TerapeutaService terapeutaService;
    private final ProfessorService professorService;
    private final ResponsavelService responsavelService;

    @PostMapping("/admin")
    public ResponseEntity<AdminResponseDto> cadastrarAdmin(@Valid @RequestBody AdminRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.cadastrarAdmin(dto));
    }

    @PostMapping("/auto-cadastro")
    public ResponseEntity<TerapeutaResponseDto> autoCadastro(@Valid @RequestBody TerapeutaRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(terapeutaService.cadastroPeloTerapeuta(dto));
    }

    @PostMapping("/cadastro-admin")
    public ResponseEntity<TerapeutaResponseDto> cadastroPeloAdmin(@RequestBody @Valid TerapeutaRequestDto dto){
        TerapeutaResponseDto responseDto = terapeutaService.cadastroPeloAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/professor")
    public ResponseEntity<ProfessorResponseDto> cadastrarProfessor(@Valid @RequestBody ProfessorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.cadastrarProfessor(dto));
    }

    @PostMapping("/paciente-responsavel")
    public ResponseEntity<ResponsavelResponseDto> cadastrarPacienteEResponsavel(@Valid @RequestBody PacienteEResponsavelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.cadastrarPacienteEResponsavel(dto));
    }
}
