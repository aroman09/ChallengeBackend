package com.nttdata.app.person.client.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("clients")
@AllArgsConstructor
public class Client {
    @Id
    @Column("idClient")
    private Long clientId;
    @Column("idPerson") // Clave foránea
    private Long personId;
    private String password;
    private boolean state;
}
