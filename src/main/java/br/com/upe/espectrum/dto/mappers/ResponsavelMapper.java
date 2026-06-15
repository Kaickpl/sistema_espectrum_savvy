package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.PacienteEResponsavelRequestDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Responsavel;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ResponsavelMapper {
    public Responsavel PacienteEResponsavelRequestDto(PacienteEResponsavelRequestDto dto, Paciente pacienteVinculado, Usuario userbase){
        if(dto == null) return null;

        Responsavel responsavel = new Responsavel();
        responsavel.setUsuario(userbase);
        responsavel.setGrauParentesco(dto.grauParentesco());

        VinculoTerapeuta vinculoTerapeuta = new VinculoTerapeuta();
        vinculoTerapeuta.setUsuario(userbase);
        vinculoTerapeuta.setDataVinculo(LocalDate.now());
        vinculoTerapeuta.setPaciente(pacienteVinculado);

        responsavel.getUsuario().getVinculoTerapeutas().add(vinculoTerapeuta);

        return responsavel;
    }

//    public Responsavel responsavelAvulsoRequestDtoToEntity(ResponsavelAvulsoRequestDto dto, Paciente paciente){
//        if(dto == null) return null;
//
//        Usuario novoUsuario = new Usuario();
//        novoUsuario.setNome(dto.nome());
//        novoUsuario.setEmail(dto.email());
//        novoUsuario.setSenha(dto.senha());
//        novoUsuario.setCpf(dto.cpf());
//        novoUsuario.setNumeroTelefone(dto.numeroTelefone());
//
//        return responsavelAvulsoRequestDtoToEntity(dto, paciente, novoUsuario);
//    }


    public ResponsavelResponseDto entityToResponseDto(Responsavel responsavel){
        if(responsavel == null) return null;

        List<PacienteResumoResponseDto> pacientes = responsavel.getUsuario().getVinculoTerapeutas().stream()
                .map(vinculoTerapeuta ->
        {
            Paciente p = vinculoTerapeuta.getPaciente();
            return new PacienteResumoResponseDto(p.getId(), p.getNome(), p.getGenero(), p.getGrauAutismo());
        }).toList();

        ResponsavelResponseDto response = new ResponsavelResponseDto(
                responsavel.getId(),
                responsavel.getUsuario().getNumeroTelefone(),
                responsavel.getUsuario().getEmail(),
                responsavel.getUsuario().getNome(),
                responsavel.getGrauParentesco(),
                pacientes
        );
        return response;
    }
}
