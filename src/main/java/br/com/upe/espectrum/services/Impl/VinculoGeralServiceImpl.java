    package br.com.upe.espectrum.services.Impl;
    import br.com.upe.espectrum.dto.requestDtos.VinculoRequestDto;
    import br.com.upe.espectrum.entities.*;
    import br.com.upe.espectrum.services.*;
    import br.com.upe.espectrum.services.paciente.PacienteService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.server.ResponseStatusException;

    import java.time.LocalDate;

    @Service
    @RequiredArgsConstructor
    public class VinculoGeralServiceImpl implements VinculoGeralService {

        private final PacienteService pacienteService;
        private final UsuarioService usuarioService;
        private final VinculoEscolarService vinculoEscolarService;
        private final VinculoTerapeutaService vinculoTerapeutaService;
        private final VinculoResponsavelService vinculoResponsavelService;

        @Override
        @Transactional
        public void criarVinculo(VinculoRequestDto dto) {
            Paciente paciente = pacienteService.mostrarPacienteEntity(dto.idPaciente());

            Usuario usuario = usuarioService.buscarUsuarioEntity(dto.idUsuario());

            switch (usuario.getTipo()){

                case ROLE_TERAPEUTA -> {
                    VinculoTerapeuta vinculoTerapeuta = new VinculoTerapeuta();
                    vinculoTerapeuta.setUsuario(usuario);
                    vinculoTerapeuta.setPaciente(paciente);
                    vinculoTerapeuta.setDataVinculo(LocalDate.now());
                    vinculoTerapeutaService.criarVinculo(vinculoTerapeuta);
                }

                case ROLE_RESPONSAVEL -> {
                    if(dto.grauParentesco() == null || dto.grauParentesco().isBlank()){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O grau de parentesco é obrigatório para vínculos de responsáveis.");
                    }

                    VinculoResponsavel vinculoResponsavel = new VinculoResponsavel();
                    vinculoResponsavel.setUsuario(usuario);
                    vinculoResponsavel.setPaciente(paciente);
                    vinculoResponsavel.setGrauParentesco(dto.grauParentesco());
                    vinculoResponsavel.setDataVinculo(LocalDate.now());
                    vinculoResponsavelService.criarVinculoResponsavel(vinculoResponsavel);
                }

                case ROLE_PROFESSOR -> {
                    if(dto.anoLetivo() == null || dto.anoLetivo().isBlank()){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ano letivo é obrigatório para vinculos de professores.");
                    }

                    VinculoEscolar vinculoEscolar = new VinculoEscolar();
                    vinculoEscolar.setPaciente(paciente);
                    vinculoEscolar.setUsuario(usuario);
                    vinculoEscolar.setDataInicio(LocalDate.now());
                    vinculoEscolar.setAnoLetivo(dto.anoLetivo());
                    vinculoEscolarService.criarVinculo(vinculoEscolar);
                }

                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O perfil deste usuário não permite criar vínculos com pacientes.");
            }
        }
    }
