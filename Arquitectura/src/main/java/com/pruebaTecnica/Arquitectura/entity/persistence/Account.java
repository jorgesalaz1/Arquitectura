package com.pruebaTecnica.Arquitectura.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("accounts")
public class Account {
    @Id
    private int id;
    private String numberAccount;
    private String type;
    private Double initialBalance;
    private boolean state;

    private int clientId;
}
