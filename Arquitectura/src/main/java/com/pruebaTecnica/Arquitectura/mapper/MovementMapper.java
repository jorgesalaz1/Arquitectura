package com.pruebatecnica.arquitectura.mapper;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.entity.persistence.Movement;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovementMapper {

    public static MovementDto convertToDto(Movement movement) {
        if (movement == null) {
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


    public static Movement convertToEntity(MovementDto movementDto) {
        if (movementDto == null) {
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
