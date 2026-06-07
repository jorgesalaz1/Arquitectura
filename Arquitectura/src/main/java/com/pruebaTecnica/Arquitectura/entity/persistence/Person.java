package com.pruebaTecnica.Arquitectura.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table("clients")
public class Person {
    @Id
    private int id;
    private String name;
    private String gender;
    private String identification;
    private String address;
    private String phoneNumber;

}
