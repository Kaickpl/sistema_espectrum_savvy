package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Envia email via API HTTP do Brevo (porta 443) em vez de SMTP direto.
 * Necessario porque provedores PaaS gratuitos (Render, Railway, etc.)
 * costumam bloquear conexoes de saida nas portas SMTP (25/465/587),
 * mesmo para servicos legitimos de relay como Gmail ou Brevo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    private final RestTemplate restTemplate;

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${spring.mail.username}")
    private String remetente;

    @Override
    public void enviarEmail(String[] destinatarios, String assunto, String corpo) {
        List<Map<String, String>> to = Stream.of(destinatarios)
                .map(String::trim)
                .map(email -> Map.of("email", email))
                .collect(Collectors.toList());

        Map<String, Object> payload = Map.of(
                "sender", Map.of("name", "Espectrum Savvy", "email", remetente),
                "to", to,
                "subject", assunto,
                "textContent", corpo
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);
        headers.set("accept", "application/json");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(BREVO_API_URL, request, String.class);
        } catch (Exception e) {
            log.error("Falha ao enviar email via Brevo para {}: {}", String.join(",", destinatarios), e.getMessage());
            throw e;
        }
    }
}
