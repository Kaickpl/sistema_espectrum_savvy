package br.com.upe.espectrum.services.Impl;

import br.com.upe.espectrum.services.CpfValidatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CpfValidatorServiceImpl implements CpfValidatorService {
    @Value("${invertexto.api.token}")
    private String token;

    private static final String VALIDATOR_URL = "https://api.invertexto.com/v1/validator";

    private final RestTemplate restTemplate;

    public CpfValidatorServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean isCpfValido(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        String url =
                VALIDATOR_URL +
                        "?token=" + token +
                        "&value=" + cpfLimpo +
                        "&type=cpf";
        System.out.println("TOKEN: " + token);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> resposta = restTemplate.getForObject(url, Map.class);

            if (resposta == null) {
                throw new IllegalStateException("API de validação de CPF não retornou resposta");
            }

            Object valido = resposta.get("valid");
            return Boolean.TRUE.equals(valido);

        } catch (HttpClientErrorException.Unauthorized e) {
            throw new IllegalStateException("Token da API de validação de CPF inválido ou expirado", e);
        } catch (RestClientException e) {
            throw new IllegalStateException("Falha ao consultar a API de validação de CPF", e);
        }
    }
}
