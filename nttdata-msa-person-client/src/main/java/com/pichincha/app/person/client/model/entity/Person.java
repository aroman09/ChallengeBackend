package com.pichincha.app.person.client.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("personas")
public class Person {
    @Id
    @Column("personId")
    private Long personId;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String direction;
    private String telephone;

}
