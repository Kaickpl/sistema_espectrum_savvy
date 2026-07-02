package br.com.upe.espectrum.dto.responseDtos;

public record EnderecoResponseDTO(
        String cep,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
) {
}
