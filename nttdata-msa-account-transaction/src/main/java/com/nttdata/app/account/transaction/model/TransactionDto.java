package com.nttdata.app.account.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.account.transaction.utils.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TransactionDto {
    @JsonProperty("typeTransaction")
    @NotBlank(message = "El parametro tipo movimiento no debe estar en blanco y debe ser DEBITO o CREDITO")
    private TransactionType type;
    @NotBlank(message = "El parametro valor no debe estar en blanco")
    private Double mount;
    private Double balance;
    private Double initialBalance;
    @JsonProperty("accountNumber")
    @NotBlank(message = "El parametro cuenta no debe estar en blanco")
    @Size(max = 20, message = "El parametro cuenta debe tener maximo 20 caracteres")
    private String account;
}
