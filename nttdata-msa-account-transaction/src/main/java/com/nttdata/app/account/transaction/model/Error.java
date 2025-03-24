package com.nttdata.app.account.transaction.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String code;
    private String message;
    private Object details;
}
