package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dto.mappers.PacienteMapper;
import br.com.upe.espectrum.dto.mappers.ResponsavelMapper;
import br.com.upe.espectrum.dto.requestDtos.PacienteEResponsavelRequestDto;
import br.com.upe.espectrum.dto.requestDtos.VinculoRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.entities.*;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.ResponsavelRepository;
import br.com.upe.espectrum.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ResponsavelServiceImpl implements ResponsavelService {
    private final UsuarioService usuarioService;
    private final ResponsavelRepository responsavelRepository;
    private final AdminRepository adminRepository;
    private final PacienteRepository pacienteRepository;
    private final ResponsavelMapper responsavelMapper;
    private final PacienteMapper pacienteMapper;
    private final TerapeutaService terapeutaService;
    private final VinculoGeralService vinculoGeralService;
    private final PasswordEncoder passwordEncoder;
    private final CpfValidatorService cpfValidatorService;


    @Transactional
    @Override
    public ResponsavelResponseDto cadastrarPacienteEResponsavel(PacienteEResponsavelRequestDto dto) {

        Admin admin = adminRepository.findById(dto.infosPaciente().adminId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado com o id "+ dto.infosPaciente().adminId()));

        Paciente paciente = pacienteMapper.requestDtoToEntity(dto.infosPaciente());
        paciente.setAdmin(admin);

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        String senhaPadraoCriptografada = passwordEncoder.encode("senha123");

        Usuario userBase = usuarioService.criarUsuario(
                dto.nomeResponsavel(),
                dto.numeroTelefone(),
                dto.emailResponsavel(),
                senhaPadraoCriptografada,
                dto.cpfResponsavel(),
                Perfil.ROLE_RESPONSAVEL,
                true
        );

        Responsavel responsavelNovo = responsavelMapper.PacienteEResponsavelRequestDto(dto, pacienteSalvo , userBase);

        // 🟢 1. Vincula ao responsavel recém-criado
        VinculoRequestDto vinculoResponsavelDto = new VinculoRequestDto(pacienteSalvo.getId(), userBase.getId(), dto.grauParentesco(), null);
        vinculoGeralService.criarVinculo(vinculoResponsavelDto);

        // 🟢 2. Vincula ao terapeuta logado (REMOVI OS COMENTÁRIOS DAQUI)
        VinculoRequestDto vinculoTerapeutaRequestDto = new VinculoRequestDto(pacienteSalvo.getId(), null, null, null);
        vinculoGeralService.criarVinculo(vinculoTerapeutaRequestDto);

        responsavelRepository.save(responsavelNovo);
        return responsavelMapper.entityToResponseDto(responsavelNovo, pacienteSalvo);
    }
}