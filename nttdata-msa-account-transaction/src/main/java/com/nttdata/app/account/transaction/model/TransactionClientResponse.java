package com.nttdata.app.account.transaction.model;

import com.nttdata.app.account.transaction.utils.AccountType;
import com.nttdata.app.account.transaction.utils.TransactionType;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class TransactionClientResponse {
    private Timestamp dateTransaction;
    private TransactionType typeTransaction;
    private Double mount;
    private Double balance;
    private String name;
    private String number;
    private AccountType typeAccount;
    private Double initialBalance;
    private Boolean status;
}
