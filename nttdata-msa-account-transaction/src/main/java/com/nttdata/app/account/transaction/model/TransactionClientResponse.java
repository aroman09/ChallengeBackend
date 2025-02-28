package com.nttdata.app.account.transaction.model;

import com.nttdata.app.account.transaction.utils.AccountType;
import lombok.Data;

import java.time.LocalDate;



@Data
public class TransactionClientResponse {
    private Long idTransaction;
    private LocalDate dateTransaction;
    private String name;
    private String number;
    private AccountType typeAccount;
    private Double initialBalance;
    private Boolean status;
    private Double mount;
    private Double balance;





}
