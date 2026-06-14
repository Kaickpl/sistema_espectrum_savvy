package br.com.upe.espectrum.dto.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String request;
    public ExceptionResponseDTO(String message, int status, String request) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
        this.request = request;
    }
}
