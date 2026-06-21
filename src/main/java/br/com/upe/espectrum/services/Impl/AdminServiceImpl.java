package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.AdminMapper;
import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.exceptions.CpfInvalidoEcxeption;
import br.com.upe.espectrum.exceptions.UsuarioExistenteException;
import br.com.upe.espectrum.repositories.AdminRepository;
import br.com.upe.espectrum.services.AdminService;
import br.com.upe.espectrum.services.CpfValidatorService;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UsuarioService usuarioService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminResponseDto cadastrarAdmin(AdminRequestDto dto) {
        if (dto.email() == null || dto.email().isBlank()){
            throw new CampoObrigatorioException("O campo de e-mail é obrigatório");
        }
        if (adminRepository.findByUsuarioEmail(dto.email()).isPresent()){
            throw new UsuarioExistenteException("Usuário já cadastrado com esse email");
        }
        if (dto.cpf() == null || dto.cpf().isBlank()){
            throw new CampoObrigatorioException("O campo de cpf é obrigatório");
        }
        if (adminRepository.findByUsuarioCpf(dto.cpf()).isPresent()){
            throw new UsuarioExistenteException("Usuário já cadastrado com esse cpf");
        }
        if (dto.nome() == null || dto.nome().isBlank()){
            throw new CampoObrigatorioException("O campo de nome é obrigatório");
        }
        if (dto.numeroTelefone() == null || dto.numeroTelefone().isBlank()){
            throw new CampoObrigatorioException("O campo de número de telefone é obrigatório");
        }
        if (dto.senha() == null || dto.senha().isBlank()){
            throw new CampoObrigatorioException("O campo de senha é obrigatório");
        }

        String hash = passwordEncoder.encode(dto.senha());

        Usuario userBase = usuarioService.criarUsuario(
                dto.nome(), dto.numeroTelefone(), dto.email(),
                hash, dto.cpf(), Perfil.ROLE_ADMIN, true
        );
        Admin adminNovo = adminMapper.requestDtoToEntity(dto);
        adminNovo.setUsuario(userBase);

        String codigoAleatorio = java.util.UUID.randomUUID().toString().substring(0 , 6).toUpperCase();
        adminNovo.setCodigoConvite(codigoAleatorio);

        adminRepository.save(adminNovo);
        return adminMapper.entityToResponseDto(adminNovo);
    }

    @Override
    public Admin buscarAdminEntityPorCodigo(String codigoConvite) {
        return adminRepository.findByCodigoConvite(codigoConvite)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado pelo código de convite informado."));
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