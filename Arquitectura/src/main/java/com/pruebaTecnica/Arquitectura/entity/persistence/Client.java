package com.pruebatecnica.arquitectura.entity.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table("clients")
public class Client extends Person {
    private String password;
    private boolean state;

    public Client(int id, String name, String gender, String identification, String address, String phoneNumber, String password, boolean state) {
        super(id, name, gender, identification, address, phoneNumber);
        this.password = password;
        this.state = state;
    }
}
