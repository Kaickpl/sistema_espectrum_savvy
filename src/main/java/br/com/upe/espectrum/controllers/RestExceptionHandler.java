package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.responseDtos.ExceptionResponseDTO;
import br.com.upe.espectrum.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InformacaoNaoEncontradoException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerInformacaoNaoEncontrada(InformacaoNaoEncontradoException ex, HttpServletRequest request) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatus()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(CampoSenhaInvalida.class)
    public ResponseEntity<String> handlerCamposSenhaInvalida(CampoSenhaInvalida ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
    @ExceptionHandler(CampoObrigatorioException.class)
    public ResponseEntity<String> handlerCampoObrigatorio(CampoObrigatorioException ex, HttpServletRequest request) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(EmailInvalidoException.class)
    public ResponseEntity<String> handlerEmailInvalido(EmailInvalidoException ex, HttpServletRequest request){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(SenhasNaoConferemException.class)
    public ResponseEntity<String> handlerSenhasNaoConferem(SenhasNaoConferemException ex, HttpServletRequest request){
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerUsuarioExistente(UsuarioExistenteException ex, HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatus()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(InformacaoExistenteException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerInformacaoExistente(InformacaoExistenteException ex, HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatus()).body(exceptionResponseDTO);
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    public ResponseEntity<String> handlerOperacaoNaoPermitida(OperacaoNaoPermitida ex, HttpServletRequest request){
        return ResponseEntity.status(400).body(ex.getMessage());
    }
    @ExceptionHandler(CpfInvalidoEcxeption.class)
    public ResponseEntity<ExceptionResponseDTO> handlerCpfinvalidoEcxe(CpfInvalidoEcxeption ex, HttpServletRequest request){
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(ex.getMessage(), 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatus()).body(exceptionResponseDTO);
    }
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        int status = ex.getStatusCode().value();
        String message = ex.getReason() != null ? ex.getReason() : ex.getMessage();
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(message, status, request.getRequestURI());
        return ResponseEntity.status(status).body(exceptionResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(message, 400, request.getRequestURI());
        return ResponseEntity.status(exceptionResponseDTO.getStatus()).body(exceptionResponseDTO);
    }


}
