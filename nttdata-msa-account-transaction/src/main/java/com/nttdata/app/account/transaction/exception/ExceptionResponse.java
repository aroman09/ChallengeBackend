package com.nttdata.app.account.transaction.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse extends RuntimeException {
    private final HttpStatus status;

    public ExceptionResponse(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
