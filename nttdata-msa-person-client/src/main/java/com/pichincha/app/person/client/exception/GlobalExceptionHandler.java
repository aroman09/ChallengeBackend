package com.pichincha.app.person.client.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pichincha.app.person.client.model.Error;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleGeneralValidArgument(WebExchangeBindException ex) {
        List<Map<String, String>> details = ex.getFieldErrors().stream()
                .map(error -> Map.of(
                        "parameter", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .collect(Collectors.toList());
        return new Error().builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Parametro no cumple condiciones validas")
                .details(details)
                .build();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Error handleGeneralDatabase(DataAccessException ex) {
        return new Error().builder()
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .message("Error en base de datos")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIllegalArgument(IllegalArgumentException ex) {
        return new Error().builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Entrada invalida")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleJsonProcessingException(JsonProcessingException ex) {
        return new Error().builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Hubo un error al deserializar el JSON")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIOException(IOException ex) {
        return new Error().builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Hubo un problema al procesar la entrada/salida de datos")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<Error> handleRuntimeException(ExceptionResponse ex) {
        Error error = new Error().builder()
                .code(ex.getStatus().value())
                .message(ex.getMessage())
                .details(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Error> handleMethodNotAllowed(MethodNotAllowedException ex) {
       Error error = new Error().builder()
                .code(ex.getStatusCode().value())
                .message("Metodo no permitido")
                .details("Metodo no permitido")
                .build();
       return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleMethodExcepcion(Exception ex) {
        return new Error().builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Ha ocurrido un error inesperado")
                .details(ex.getMessage())
                .build();

    }
}
