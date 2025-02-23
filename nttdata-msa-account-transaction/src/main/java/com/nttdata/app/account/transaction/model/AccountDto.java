package com.nttdata.app.account.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.account.transaction.utils.AccountType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDto {
    private Long idAccount;
    @JsonProperty("numberAccount")
    @NotBlank(message = "El numero de cuenta no debe estar en blanco")
    private String number;
    @JsonProperty("typeAccount")
    @NotBlank(message = "El tipo de cuenta no debe estar en blanco y debe ser AHORRO o CREDITO")
    private AccountType type;
    @Min(value = 1, message = "El saldo inicial debe ser mayor a cero")
    private Double initialBalance;
    private Boolean status;
    private Client client;
}
