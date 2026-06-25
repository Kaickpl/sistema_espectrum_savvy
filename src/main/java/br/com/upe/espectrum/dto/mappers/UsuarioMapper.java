package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Component;


@Component
public class UsuarioMapper {
    public UsuarioResponseDTO entityToResponseDto(Usuario usuario){
        String codigoConvite = usuario.getPerfilAdmin() != null ? usuario.getPerfilAdmin().getCodigoConvite() : null;
        UsuarioResponseDTO response = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNumeroTelefone(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTipo(),
                usuario.isActive(),
                codigoConvite
        );

        return response;
    }

    public Usuario updateDtoToEntity(UsuarioUpdateDto dto){
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setEmail(dto.email());
        novoUsuario.setNumeroTelefone(dto.numeroTelefone()

        );

        return  novoUsuario;
    }

}

