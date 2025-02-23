package com.nttdata.app.account.transaction.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
                .code("990")
                .message("Parametro no cumple condiciones validas")
                .details(details)
                .build();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Error handleGeneralDatabase(DataAccessException ex) {
        return new Error().builder()
                .code("991")
                .message("Error en base de datos")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIllegalArgument(IllegalArgumentException ex) {
        return new Error().builder()
                .code("992")
                .message("Entrada invalida")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleJsonProcessingException(JsonProcessingException ex) {
        return new Error().builder()
                .code("993")
                .message("Hubo un error al deserializar el JSON")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleIOException(IOException ex) {
        return new Error().builder()
                .code("994")
                .message("Hubo un problema al procesar la entrada/salida de datos")
                .details(ex.getMessage())
                .build();
    }

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<Error> handleRuntimeException(ExceptionResponse ex) {
        Error error = new Error().builder()
                .code("994")
                .message(ex.getMessage())
                .details(ex.getMessage())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Error handleMethodNotAllowed(MethodNotAllowedException ex) {
        return new Error().builder()
                .code("999")
                .message("Metodo no permitido")
                .details("Metodo no permitido")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleMethodExcepcion(Exception ex) {
        return new Error().builder()
                .code("999")
                .message("Ha ocurrido un error inesperado")
                .details(ex.getMessage())
                .build();
    }
}
