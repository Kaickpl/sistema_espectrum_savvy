package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.dto.mappers.UsuarioMapper;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public Usuario criarUsuario(String nome, String numeroTelefone, String email, String senha, String cpf, Perfil tipo, boolean isActive) {
        if(usuarioRepository.existsByEmail(email)){
            throw new RuntimeException("Este e-mail já está em uso.");
        }

        if(usuarioRepository.existsByNumeroTelefone(numeroTelefone)){
            throw new RuntimeException("Este número de telefone já está em uso");
        }

        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNumeroTelefone(numeroTelefone);
        usuarioNovo.setEmail(email);
        usuarioNovo.setNome(nome);
        //temporario antes da segurança
        usuarioNovo.setSenha(senha);
        usuarioNovo.setCpf(cpf);
        usuarioNovo.setTipo(tipo);
        usuarioNovo.setActive(true);

        usuarioRepository.save(usuarioNovo);
        return usuarioNovo;
    }

    @Override
    public Usuario buscarUsuario(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuário não encontrado com o id: " + id));
    }

    @Override
    public UsuarioResponseDTO editarPerfilUsuario(UUID id, UsuarioUpdateDto dto) {
        Usuario usuarioAntigo = this.buscarUsuario(id);
        usuarioAntigo.setNome(dto.nome());
        usuarioAntigo.setEmail(dto.email());
        usuarioAntigo.setNumeroTelefone(dto.numeroTelefone());
        Usuario novoUsuario = usuarioRepository.save(usuarioAntigo);
        return usuarioMapper.entityToResponseDto(novoUsuario);
    }

    @Override
    public void desativarUsuario(UUID id) {
        Usuario usuario = this.buscarUsuario(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public void ativarUsuario(UUID id) {
        Usuario usuario = this.buscarUsuario(id);
        usuario.setActive(true);
        usuarioRepository.save(usuario);
    }
}
