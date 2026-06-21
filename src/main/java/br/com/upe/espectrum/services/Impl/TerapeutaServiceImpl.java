package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.TerapeutaMapper;
import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.Perfil;
import br.com.upe.espectrum.entities.enums.StatusCadastro;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.exceptions.CpfInvalidoEcxeption;
import br.com.upe.espectrum.exceptions.UsuarioExistenteException;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import br.com.upe.espectrum.services.AdminService;
import br.com.upe.espectrum.services.CpfValidatorService;
import br.com.upe.espectrum.services.TerapeutaService;
import br.com.upe.espectrum.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TerapeutaServiceImpl implements TerapeutaService {

    private final UsuarioService usuarioService;
    private final TerapeutaRepository terapeutaRepository;
    private final TerapeutaMapper terapeutaMapper;
    private final AdminService adminService;
    private final CpfValidatorService cpfValidatorService;


    @Transactional
    @Override
    public TerapeutaResponseDto cadastrarTerapeuta(TerapeutaRequestDto dto) {
        Admin admin = adminService.buscarAdminEntity(dto.idAdmin());
        if (dto.email()==null||dto.email().isBlank()){
            throw new CampoObrigatorioException("Campo de Email é obrigatório");
        }
        if (dto.cpf()==null||dto.cpf().isBlank()){
            throw new CampoObrigatorioException("Campo de cpf é obrigatório");
        }
        if (dto.nome()==null||dto.nome().isBlank()){
            throw new CampoObrigatorioException("Campo de nome é obrigatório");
        }
        if (dto.numeroTelefone()==null||dto.numeroTelefone().isBlank()){
            throw new CampoObrigatorioException("Campo de número de telefone é obrigatório");
        }
        if (dto.senha()==null||dto.senha().isBlank()){
            throw new CampoObrigatorioException("Campo de senha é obrigatório");
        }
        if (terapeutaRepository.findByUsuarioCpf(dto.cpf()).isPresent()){
            throw new UsuarioExistenteException("Já existe um terapeuta com esse cpf" + dto.cpf());
        }
        if (terapeutaRepository.findByUsuarioEmail(dto.email()).isPresent()){
            throw new UsuarioExistenteException("Já existe um terapeuta com esse email: " + dto.email());
        }

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
        novoTerapeuta.setAdmin(admin);
        terapeutaRepository.save(novoTerapeuta);
        return terapeutaMapper.entityToResponseDto(novoTerapeuta);
    }

    @Override
    public TerapeutaResponseDto buscarTerapeuta(UUID idTerapeuta) {
        Terapeuta response = terapeutaRepository.findById(idTerapeuta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terapeuta não encontrado com o id " +idTerapeuta));

        return terapeutaMapper.entityToResponseDto(response);
    }

    @Override
    public TerapeutaResponseDto aprovarCadastroTerapeuta(UUID idTerapeuta) {
        Terapeuta terapeuta = this.getTerapeuta(idTerapeuta);

        if(terapeuta.getStatusCadastro() == StatusCadastro.APROVADO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cadastro já esta aprovado.");
        }

        terapeuta.setStatusCadastro(StatusCadastro.APROVADO);
        Terapeuta terapeutaSalvo = terapeutaRepository.save(terapeuta);
        return terapeutaMapper.entityToResponseDto(terapeutaSalvo);
    }

    @Override
    public List<TerapeutaResponseDto> buscarTerapeutasPorAdm(UUID idAdmin) {
        List<Terapeuta> terapeutasDoAdmin = terapeutaRepository.findByAdminId(idAdmin);
        return terapeutasDoAdmin.stream().map(terapeutaMapper::entityToResponseDto).toList();
    }

    @Override
    public List<TerapeutaResponseDto> buscarTerapeutasPendentesPorAdmin(UUID idAdmin) {
        List<Terapeuta> terapeutasPendentesDoAdmin = terapeutaRepository.findByStatusCadastroAndAdminId(StatusCadastro.PENDENTE, idAdmin);
        return terapeutasPendentesDoAdmin.stream().map(terapeutaMapper::entityToResponseDto).toList();
    }

    @Override
    @Transactional
    public TerapeutaResponseDto reativarContaTerapeuta(UUID idTerapeuta) {
        usuarioService.reativarUsuario(idTerapeuta);
        terapeutaRepository.alterarStatusDiretoNoBanco(idTerapeuta, true);

        return this.buscarTerapeuta(idTerapeuta);
    }

    @Override
    @Transactional
    public void desativarContaTerapeuta(UUID id) {
        terapeutaRepository.alterarStatusDiretoNoBanco(id, false);
        usuarioService.desativarUsuario(id);
    }


    private Terapeuta getTerapeuta(UUID idTerapeuta){
        Terapeuta response = terapeutaRepository.findById(idTerapeuta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terapeuta não encontrado com o id " +idTerapeuta));

        return response;
    }

}
