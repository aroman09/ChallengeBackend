package com.nttdata.app.account.transaction.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("accounts")
public class Account {
    @Id
    @Column("id_account")
    private Long accountId;
    @Column("id_client")
    private Long clientId;
    private String number;
    private String type;
    @Column("initial_balance")
    private Double initialBalance;
    private Boolean status;
}
