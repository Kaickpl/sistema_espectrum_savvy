package br.com.upe.espectrum.dto.mappers;

import br.com.upe.espectrum.dto.requestDtos.PacienteEResponsavelRequestDto;
import br.com.upe.espectrum.dto.responseDtos.PacienteResumoResponseDto;
import br.com.upe.espectrum.dto.responseDtos.ResponsavelResponseDto;
import br.com.upe.espectrum.entities.Paciente;
import br.com.upe.espectrum.entities.Responsavel;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponsavelMapper {
    public Responsavel PacienteEResponsavelRequestDto(PacienteEResponsavelRequestDto dto, Paciente pacienteVinculado, Usuario userbase){
        if(dto == null) return null;

        Responsavel responsavel = new Responsavel();
        responsavel.setUsuario(userbase);
        responsavel.setGrauParentesco(dto.grauParentesco());

        return responsavel;
    }

    public ResponsavelResponseDto entityToResponseDto(Responsavel responsavel, Paciente paciente){
        if(responsavel == null || paciente == null) return null;

        PacienteResumoResponseDto pacienteResumo = new PacienteResumoResponseDto(
                paciente.getId(),
                paciente.getNome(),
                paciente.getGenero(),
                paciente.getGrauAutismo()
        );

        return new ResponsavelResponseDto(
                responsavel.getId(),
                responsavel.getUsuario().getNumeroTelefone(),
                responsavel.getUsuario().getEmail(),
                responsavel.getUsuario().getNome(),
                responsavel.getGrauParentesco(),
                List.of(pacienteResumo)
        );
    }
}