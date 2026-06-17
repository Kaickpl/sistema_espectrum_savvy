package br.com.upe.espectrum.dto.mappers;
import br.com.upe.espectrum.dto.responseDtos.VinculoTerapeutaResponseDto;
import br.com.upe.espectrum.entities.VinculoTerapeuta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VinculoTerapeutaMapper {
    private final PacienteMapper pacienteMapper;
    private final TerapeutaMapper terapeutaMapper;

    public VinculoTerapeutaResponseDto entityToResponseDto(VinculoTerapeuta vinculo){
        VinculoTerapeutaResponseDto response = new VinculoTerapeutaResponseDto(
                vinculo.getId(),
                pacienteMapper.entityToResponseDto(vinculo.getPaciente()),
                terapeutaMapper.entityToResponseDto(vinculo.getUsuario().getPerfilTerapeuta()),
                vinculo.getDataVinculo()
                );

        return response;
    }
}
