package br.com.upe.espectrum.services.Impl;
import br.com.upe.espectrum.dto.mappers.VinculoTerapeutaMapper;
import br.com.upe.espectrum.dto.responseDtos.PacienteResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteVinculadoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.Admin;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import br.com.upe.espectrum.repositories.PacienteRepository;
import br.com.upe.espectrum.repositories.TerapeutaRepository;
import br.com.upe.espectrum.repositories.VinculoTerapeutaRepository;
import br.com.upe.espectrum.services.UsuarioService;
import br.com.upe.espectrum.services.VinculoTerapeutaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VinculoTerapeutaServiceImpl implements VinculoTerapeutaService {

    private final VinculoTerapeutaRepository vinculoTerapeutaRepository;
    private final VinculoTerapeutaMapper vinculoTerapeutaMapper;
    private final TerapeutaRepository terapeutaRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioService usuarioService;
    //mapper

    @Override
    public VinculoTerapeutaResponseDto buscarVinculo(UUID idVinculo) {
        VinculoTerapeuta vinculo = vinculoTerapeutaRepository.findById(idVinculo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vinculo não encontrado com o id "+idVinculo));

        return vinculoTerapeutaMapper.entityToResponseDto(vinculo);
    }

    @Override
    public VinculoTerapeutaResponseDto criarVinculo(VinculoTerapeuta vinculoTerapeuta) {
        return vinculoTerapeutaMapper.entityToResponseDto(vinculoTerapeutaRepository.save(vinculoTerapeuta));
    }

    @Override
    public List<PacienteVinculadoResponseDto> listarPacientesVinculados(UUID idTerapeuta) {
        Terapeuta terapeuta = getTerapeuta(idTerapeuta);
        verificarPermissaoAdmin(terapeuta.getAdmin().getId());

        return vinculoTerapeutaRepository.findByUsuarioId(idTerapeuta).stream()
                .map(vinculo -> new PacienteVinculadoResponseDto(
                        vinculo.getId(),
                        vinculo.getPaciente().getId(),
                        vinculo.getPaciente().getNome(),
                        vinculo.getPaciente().getGenero(),
                        vinculo.getPaciente().getGrauAutismo()
                ))
                .toList();
    }

    @Override
    public List<PacienteResumoResponseDto> listarPacientesDisponiveis(UUID idTerapeuta) {
        Terapeuta terapeuta = getTerapeuta(idTerapeuta);
        verificarPermissaoAdmin(terapeuta.getAdmin().getId());

        List<UUID> idsVinculados = vinculoTerapeutaRepository.findByUsuarioId(idTerapeuta).stream()
                .map(vinculo -> vinculo.getPaciente().getId())
                .toList();

        List<Paciente> disponiveis = idsVinculados.isEmpty()
                ? pacienteRepository.findByAdminId(terapeuta.getAdmin().getId())
                : pacienteRepository.findByAdminIdAndIdNotIn(terapeuta.getAdmin().getId(), idsVinculados);

        return disponiveis.stream()
                .map(paciente -> new PacienteResumoResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getGenero(),
                        paciente.getGrauAutismo()
                ))
                .toList();
    }

    @Override
    public List<PacienteResumoResponseDto> listarMeusPacientesVinculados() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return vinculoTerapeutaRepository.findByUsuarioId(usuarioLogado.getId()).stream()
                .map(vinculo -> new PacienteResumoResponseDto(
                        vinculo.getPaciente().getId(),
                        vinculo.getPaciente().getNome(),
                        vinculo.getPaciente().getGenero(),
                        vinculo.getPaciente().getGrauAutismo()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void desvincular(UUID idVinculo) {
        VinculoTerapeuta vinculo = vinculoTerapeutaRepository.findById(idVinculo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vinculo não encontrado com o id " + idVinculo));

        Terapeuta terapeuta = getTerapeuta(vinculo.getUsuario().getId());
        verificarPermissaoAdmin(terapeuta.getAdmin().getId());

        vinculoTerapeutaRepository.deletarPorId(idVinculo);
    }

    private Terapeuta getTerapeuta(UUID idTerapeuta) {
        return terapeutaRepository.findById(idTerapeuta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Terapeuta não encontrado com o id " + idTerapeuta));
    }

    private Admin obterAdminLogado() {
        Usuario usuarioLogadoNoToken = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuarioCompleto = usuarioService.buscarUsuarioEntity(usuarioLogadoNoToken.getId());
        Admin adminLogado = usuarioCompleto.getPerfilAdmin();

        if(adminLogado == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem realizar esta ação.");
        }

        return adminLogado;
    }

    private void verificarPermissaoAdmin(UUID idAdmin) {
        Admin adminLogado = obterAdminLogado();

        if(!adminLogado.getId().equals(idAdmin)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado. Você não tem permissão para interagir com dados de outra clínica.");
        }
    }
}
