package com.pichincha.app.person.client.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("clientes")
public class Client {
    @Id
    @Column("clientId")
    private Long clientId;
    @Column("personId") // Clave foránea
    private Long personId;
    private String password;
    private boolean state;
}
