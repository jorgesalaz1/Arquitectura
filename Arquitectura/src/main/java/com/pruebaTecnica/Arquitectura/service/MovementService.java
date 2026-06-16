package com.pruebatecnica.arquitectura.service;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<MovementDto> getAllMovements();
    Mono<MovementDto> getMovementById(int id);
    Mono<MovementDto> postMovement(MovementDto movementDto);
    Mono<MovementDto> updateMovementById(int id, MovementDto movementDto);
    Mono<Void> deleteMovementById(int id);
    Double calculatNewBalance(double currentBalance ,MovementDto movementDto);
    Flux<MovementDto> getMovementByClientIdn(int id);

}
