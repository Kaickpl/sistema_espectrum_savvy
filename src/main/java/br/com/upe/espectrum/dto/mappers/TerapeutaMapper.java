package br.com.upe.espectrum.dto.mappers;

import br.com.upe.espectrum.dto.requestDtos.TerapeutaRequestDto;
import br.com.upe.espectrum.dto.responseDtos.TerapeutaResponseDto;
import br.com.upe.espectrum.entities.Terapeuta;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class TerapeutaMapper {
    public Terapeuta requestDtoToEntity(TerapeutaRequestDto dto){
        if(dto == null) return null;

        Terapeuta novoTerapeuta = new Terapeuta();
        novoTerapeuta.setUsuario(new Usuario());
        novoTerapeuta.setMatricula(dto.matricula());
        novoTerapeuta.setPeriodo(dto.periodo());
        novoTerapeuta.getUsuario().setNome(dto.nome());
        novoTerapeuta.getUsuario().setEmail(dto.email());
        novoTerapeuta.getUsuario().setCpf(dto.cpf());
        novoTerapeuta.getUsuario().setSenha(dto.senha());
        novoTerapeuta.getUsuario().setNumeroTelefone(dto.numeroTelefone());

        return novoTerapeuta;
    }

    public TerapeutaResponseDto entityToResponseDto(Terapeuta terapeuta){

        TerapeutaResponseDto response = new TerapeutaResponseDto(
                terapeuta.getId(),
                terapeuta.getUsuario().getNome(),
                terapeuta.getUsuario().getEmail(),
                terapeuta.getUsuario().getNumeroTelefone(),
                terapeuta.getMatricula(),
                terapeuta.getPeriodo()
        );

        return response;
    }
}
