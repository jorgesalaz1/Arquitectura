package com.pruebaTecnica.Arquitectura.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("movements")
public class Movement {
    @Id
    private int id;
    private LocalDate date;
    private String type;
    private Double amount;
    private Double balance;
    private int clientId;
    private int accountId;
}
