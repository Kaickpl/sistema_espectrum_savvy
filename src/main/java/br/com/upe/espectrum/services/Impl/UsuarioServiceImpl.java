package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.requestDtos.UsuarioUpdateDto;
import br.com.upe.espectrum.dto.responseDtos.UsuarioResponseDTO;
import br.com.upe.espectrum.dto.mappers.UsuarioMapper;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.exceptions.CpfInvalidoEcxeption;
import br.com.upe.espectrum.exceptions.EmailInvalidoException;
import br.com.upe.espectrum.exceptions.InformacaoNaoEncontradoException;
import br.com.upe.espectrum.exceptions.OperacaoNaoPermitida;
import br.com.upe.espectrum.repositories.UsuarioRepository;
import br.com.upe.espectrum.services.CpfValidatorService;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CpfValidatorService cpfValidatorService;
    private final PasswordEncoder passwordEncoder;



    @Override
    public Usuario criarUsuario(String nome, String numeroTelefone, String email, String senha, String cpf, Perfil tipo, boolean isActive) {
        if(usuarioRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está em uso.");
        }

        if(usuarioRepository.existsByNumeroTelefone(numeroTelefone)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este número de telefone já está em uso.");
        }

        if(usuarioRepository.existsByCpf(cpf)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este número de CPF já está em uso.");
        }
        if (!cpfValidatorService.isCpfValido((cpf))) {
            throw new CpfInvalidoEcxeption("O CPF informado não existe");
        }

        Usuario usuarioNovo = new Usuario();
        usuarioNovo.setNumeroTelefone(numeroTelefone);
        usuarioNovo.setEmail(email);
        usuarioNovo.setNome(nome);
        //temporario antes da segurança
        usuarioNovo.setSenha(senha);
        usuarioNovo.setCpf(cpf);
        usuarioNovo.setTipo(tipo);
        usuarioNovo.setActive(isActive);

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
    public void verificarEmail(String email) {
        usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new EmailInvalidoException("Email não encontrado")
                );
    }

    @Override
    public void desativarUsuario(UUID id) {
        usuarioRepository.alterarStatusDiretoNoBanco(id, false);
    }

    @Override
    public void reativarUsuario(UUID id) {
        usuarioRepository.alterarStatusDiretoNoBanco(id, true);
    }

    @Override
    public Usuario buscarUsuarioEntity(UUID id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado com o id "+id));

        return usuario;
    }

    @Override
    @Transactional
    public void atualizarSenhaViaToken(UUID id, String novaSenha) {
        Usuario usuario = buscarUsuario(id);
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);

    }

    @Override
    public Usuario BuscarEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new OperacaoNaoPermitida("Usuário" + email));
    }
}
