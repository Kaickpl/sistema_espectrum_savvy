package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.AdminMapper;
import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.services.AdminService;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UsuarioService usuarioService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Transactional
    public AdminResponseDto cadastrarAdmin(AdminRequestDto dto) {
        Usuario userBase = usuarioService.criarUsuario(
                dto.nome(), dto.numeroTelefone(), dto.email(),
                dto.senha(), dto.cpf(), Perfil.ROLE_ADMIN, true
        );
        Admin adminNovo = adminMapper.requestDtoToEntity(dto);
        adminNovo.setUsuario(userBase);
        adminRepository.save(adminNovo);
        return adminMapper.entityToResponseDto(adminNovo);
    }

    @Override
    public AdminResponseDto buscarAdmin(UUID idAdmin) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado com o id " + idAdmin));

        return adminMapper.entityToResponseDto(admin);
    }

    @Override
    public Admin buscarAdminEntity(UUID idAdmin) {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado com o id " + idAdmin));

        return admin;
    }

    @Override
    @Transactional
    public AdminResponseDto reativarContaAdmin(UUID idAdmin){
        usuarioService.reativarUsuario(idAdmin);

        adminRepository.alterarStatusDiretoNoBanco(idAdmin, true);

        Admin adminReativado = this.buscarAdminEntity(idAdmin);

        return adminMapper.entityToResponseDto(adminReativado);
    }

    @Override
    @Transactional
    public void desativarContaAdmin(UUID id){

        adminRepository.alterarStatusDiretoNoBanco(id, false);
        usuarioService.desativarUsuario(id);
    }
}