package br.com.upe.espectrum.dto.requestDtos;

public record UsuarioUpdateDto (
        String nome,
        String email,
        String numeroTelefone
){
}
