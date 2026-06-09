package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.AdminMapper;
import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCadastroServiceImpl {

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
}