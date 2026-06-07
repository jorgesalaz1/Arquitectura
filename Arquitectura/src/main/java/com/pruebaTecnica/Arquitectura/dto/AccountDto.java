package com.pruebaTecnica.Arquitectura.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Id
    private int id;

    @JsonProperty("number_account")
    private String numberAccount;

    private String type;

    @JsonProperty("initial_balance")
    private Double initialBalance;

    private boolean state;

    @JsonProperty("client_id")
    private int clientId;
}
