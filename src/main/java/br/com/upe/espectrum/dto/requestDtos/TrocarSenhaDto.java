package br.com.upe.espectrum.dto.requestDtos;

import br.com.upe.espectrum.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrocarSenhaDto {
    private String email;
    private String novaSenha;
    private String confirmaSenha;

    public TrocarSenhaDto(Usuario usuario) {
        this.email = usuario.getEmail();
        this.novaSenha = usuario.getSenha();
        this.confirmaSenha = usuario.getSenha();
    }

}
