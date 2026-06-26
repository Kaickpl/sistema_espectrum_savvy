package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.requestDtos.ProfessorRequestDto;
import br.com.upe.espectrum.dto.responseDtos.ProfessorResponseDto;
import br.com.upe.espectrum.entities.Professor;
import br.com.upe.espectrum.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public Professor requestDtoToEntity(ProfessorRequestDto requestDto){
        if(requestDto == null) return null;

        Professor professor = new Professor();

        professor.setEscola(requestDto.escola());
        professor.setUsuario(new Usuario());
        professor.getUsuario().setNome(requestDto.nome());
        professor.getUsuario().setEmail(requestDto.email());
        professor.getUsuario().setSenha(requestDto.senha());
        professor.getUsuario().setCpf(requestDto.cpf());
        professor.getUsuario().setNumeroTelefone(requestDto.numeroTelefone());

        return professor;
    }

    public ProfessorResponseDto entityToResponseDto(Professor professor){
        ProfessorResponseDto dto = new ProfessorResponseDto(
                professor.getUsuario().getId(),
                professor.getUsuario().getNumeroTelefone(),
                professor.getUsuario().getEmail(),
                professor.getUsuario().getNome(),
                professor.getEscola()
        );

        return dto;
    }

}
