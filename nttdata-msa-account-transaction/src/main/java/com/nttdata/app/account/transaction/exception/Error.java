package com.nttdata.app.account.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String code;
    private String message;
    private Object details;
}
