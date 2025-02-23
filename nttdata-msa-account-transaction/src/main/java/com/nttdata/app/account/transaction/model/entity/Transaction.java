package com.nttdata.app.account.transaction.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Table("transactions")
public class Transaction {
    @Id
    @Column("idTransaction")
    private Long transactionId;
    private Timestamp dateTransaction;
    private String type;
    private Double mount;
    private Double balance;
    @Column("account")
    private String account;

}
