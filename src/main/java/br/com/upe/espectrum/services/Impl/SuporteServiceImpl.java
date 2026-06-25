package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.dto.requestDtos.SuporteRequestDto;
import br.com.upe.espectrum.entities.Usuario;
import br.com.upe.espectrum.entities.enums.CategoriaSuporte;
import br.com.upe.espectrum.exceptions.CampoObrigatorioException;
import br.com.upe.espectrum.security.SecurityUtils;
import br.com.upe.espectrum.services.EmailService;
import br.com.upe.espectrum.services.SuporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuporteServiceImpl implements SuporteService {

    private final EmailService emailService;
    private final SecurityUtils securityUtils;

    @Value("${suporte.destinatarios}")
    private String destinatariosConfig;

    @Override
    public void criarSolicitacao(SuporteRequestDto dto) {
        if (dto.getCategorias() == null || dto.getCategorias().isEmpty()) {
            throw new CampoObrigatorioException("Selecione ao menos uma categoria do problema.");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new CampoObrigatorioException("Campo de descrição é obrigatório.");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new CampoObrigatorioException("Campo de email é obrigatório.");
        }

        Usuario usuarioLogado = securityUtils.getCurrentUser();

        String categorias = dto.getCategorias().stream()
                .map(CategoriaSuporte::getDescricao)
                .collect(Collectors.joining(", "));

        String corpo = """
                Nova solicitação de suporte recebida no Espectrum.

                Usuário: %s
                Email para contato: %s
                Categorias: %s

                Descrição:
                %s
                """.formatted(usuarioLogado.getNome(), dto.getEmail(), categorias, dto.getDescricao());

        String[] destinatarios = destinatariosConfig.split(",");

        emailService.enviarEmail(destinatarios, "Nova solicitação de suporte - Espectrum", corpo);
    }
}
