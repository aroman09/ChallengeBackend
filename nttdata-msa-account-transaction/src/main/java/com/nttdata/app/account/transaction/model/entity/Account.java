package com.nttdata.app.account.transaction.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("accounts")
public class Account {
    @Id
    @Column("idAccount")
    private Long accountId;
    @Column("idClient")
    private Long clientId;
    private String number;
    private String type;
    private Double initialBalance;
    private Boolean status;
}
