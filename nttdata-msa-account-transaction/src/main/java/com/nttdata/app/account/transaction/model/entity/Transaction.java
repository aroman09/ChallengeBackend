package com.nttdata.app.account.transaction.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Table("transactions")
public class Transaction {
    @Id
    @Column("id_transaction")
    private Long transactionId;
    @Column("date_transaction")
    private LocalDateTime dateTransaction;
    private String type;
    private Double amount;
    private Double balance;
    @Column("initial_balance")
    private Double initialBalance;
    @Column("id_account")
    private Long account;

}
