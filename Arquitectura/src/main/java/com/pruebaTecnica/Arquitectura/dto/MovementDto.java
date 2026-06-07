package com.pruebaTecnica.Arquitectura.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    @Id
    private int id;
    private LocalDate date;
    private String type;
    private Double amount;
    private Double balance;

    @JsonProperty("client_id")
    private int clientId;

    @JsonProperty("account_id")
    private int accountId;

}
