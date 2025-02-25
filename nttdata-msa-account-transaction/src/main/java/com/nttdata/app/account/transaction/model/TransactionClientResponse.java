package com.nttdata.app.account.transaction.model;

import com.nttdata.app.account.transaction.utils.AccountType;
import com.nttdata.app.account.transaction.utils.TransactionType;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
public class TransactionClientResponse {
    private Long idTransaction;
    private LocalDateTime dateTransaction;
    private TransactionType typeTransaction;
    private Double mount;
    private Double balance;
    private String name;
    private String number;
    private AccountType typeAccount;
    private Double initialBalance;
    private Boolean status;
}
