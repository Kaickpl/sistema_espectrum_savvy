package br.com.upe.espectrum.controllers;

import br.com.upe.espectrum.dto.responseDtos.ExceptionResponseDTO;
import br.com.upe.espectrum.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


}
