package com.pruebaTecnica.Arquitectura.validator;

import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MovementsValidator {
    public Mono<MovementDto> validator(MovementDto movementDto) {
        if (movementDto.getAmount() <= 0) {
            return Mono.error(new IllegalArgumentException("El valor del movimiento debe ser mayor que cero"));
        }
        if(!"Debito".equalsIgnoreCase(movementDto.getType()) &&
                !"Credito".equalsIgnoreCase(movementDto.getType())){
            return Mono.error(new IllegalArgumentException("Tipo de movimiento invalido. Use Credito o Debito"));

        }

        return Mono.just(movementDto);
    }


}
