package com.pruebaTecnica.Arquitectura.mapper;

import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Movement;

public class MovementMapper {
    public static MovementDto convertToDto(Movement movement){
        if(movement == null){
            return null;
        }
        MovementDto movementDto = new MovementDto();
        movementDto.setId(movement.getId());
        movementDto.setDate(movement.getDate());
        movementDto.setType(movement.getType());
        movementDto.setAmount(movement.getAmount());
        movementDto.setBalance(movement.getBalance());
        movementDto.setClientId(movement.getClientId());
        movementDto.setAccountId(movement.getAccountId());
        return movementDto;
    }


    public static Movement convertToEntity(MovementDto movementDto){
        if(movementDto == null){
            return null;
        }
        return new Movement(
                movementDto.getId(),
                movementDto.getDate(),
                movementDto.getType(),
                movementDto.getAmount(),
                movementDto.getBalance(),
                movementDto.getClientId(),
                movementDto.getAccountId()
        );
    }
}
