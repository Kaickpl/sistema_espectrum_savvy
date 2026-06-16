package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.AdminRequestDto;
import br.com.upe.espectrum.dto.responseDtos.AdminResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public Admin requestDtoToEntity(AdminRequestDto dto){
        if(dto == null) return null;

        Admin admin = new Admin();
        admin.setUsuario(new Usuario());
        admin.getUsuario().setNome(dto.nome());
        admin.getUsuario().setEmail(dto.email());
        admin.getUsuario().setCpf(dto.cpf());
        admin.getUsuario().setSenha(dto.senha());
        admin.getUsuario().setNumeroTelefone(dto.numeroTelefone());
        admin.setRegistroProfissional(dto.registroProfissional());

        return admin;
    }

    public AdminResponseDto entityToResponseDto(Admin admin){
        if(admin == null) return null;
        AdminResponseDto dto = new AdminResponseDto(
                admin.getId(),
                admin.getUsuario().getId(),
                admin.getUsuario().getNome(),
                admin.getUsuario().getNumeroTelefone(),
                admin.getUsuario().getEmail(),
                admin.getUsuario().getTipo()
                );

        return dto;
    }
}
