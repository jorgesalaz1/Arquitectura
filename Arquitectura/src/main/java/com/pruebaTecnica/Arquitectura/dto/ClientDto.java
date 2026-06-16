package com.pruebatecnica.arquitectura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @Id
    private int id;
    private String name;
    private String gender;
    private String identification;
    private String address;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean state;
}
