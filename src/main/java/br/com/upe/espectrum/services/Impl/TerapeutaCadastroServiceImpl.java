package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dto.mappers.TerapeutaMapper;
import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TerapeutaCadastroServiceImpl {

    private final UsuarioService usuarioService;
    private final TerapeutaRepository terapeutaRepository;
    private final TerapeutaMapper terapeutaMapper;

    @Transactional
    public TerapeutaResponseDto cadastrarTerapeuta(TerapeutaRequestDto dto) {

        Usuario userBase = usuarioService.criarUsuario(
                dto.nome(),
                dto.numeroTelefone(),
                dto.email(),
                dto.senha(),
                dto.cpf(),
                Perfil.ROLE_TERAPEUTA,
                true
        );

        Terapeuta novoTerapeuta = terapeutaMapper.requestDtoToEntity(dto);
        novoTerapeuta.setUsuario(userBase);
        terapeutaRepository.save(novoTerapeuta);
        return terapeutaMapper.entityToResponseDto(novoTerapeuta);
    }
}
