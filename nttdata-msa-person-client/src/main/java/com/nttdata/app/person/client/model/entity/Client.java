package com.nttdata.app.person.client.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("clients")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @Column("id_client")
    private Long clientId;
    @Column("id_person") // Clave foránea
    private Long personId;
    @Column("password")
    private String password;
    @Column("state")
    private boolean state;
}
