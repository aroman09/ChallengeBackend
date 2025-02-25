package com.nttdata.app.person.client.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("persons")
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Column("id_person")
    private Long personId;
    @Column("name")
    private String name;
    @Column("gender")
    private String gender;
    @Column("age")
    private Integer age;
    @Column("identification")
    private String identification;
    @Column("direction")
    private String direction;
    @Column("telephone")
    private String telephone;

}
