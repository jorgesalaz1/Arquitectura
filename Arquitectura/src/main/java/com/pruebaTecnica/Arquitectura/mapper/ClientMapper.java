package com.pruebaTecnica.Arquitectura.mapper;

import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Client;

public class ClientMapper {

    public static ClientDto convertToDto(Client client){
        if(client == null){
            return null;
        }
       ClientDto clientDto = new ClientDto();
       clientDto.setId(client.getId());
       clientDto.setName(client.getName());
       clientDto.setGender(client.getGender());
       clientDto.setIdentification(client.getIdentification());
       clientDto.setAddress(client.getAddress());
       clientDto.setPhoneNumber(client.getPhoneNumber());
       clientDto.setPassword(client.getPassword());
       clientDto.setState(client.isState());
       return clientDto;
    }


    public static Client convertToEntity(ClientDto clientDto){
        if(clientDto == null){
            return null;
        }
        return new Client(
                clientDto.getId(),
                clientDto.getName(),
                clientDto.getGender(),
                clientDto.getIdentification(),
                clientDto.getAddress(),
                clientDto.getPhoneNumber(),
                clientDto.getPassword(),
                clientDto.isState()
        );
    }
}
