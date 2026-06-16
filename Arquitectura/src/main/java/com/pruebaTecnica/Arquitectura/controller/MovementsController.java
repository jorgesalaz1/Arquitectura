package com.pruebatecnica.arquitectura.controller;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.service.MovementService;
import com.pruebatecnica.arquitectura.validator.MovementsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class MovementsController {
    private final MovementService movementService;
    private final MovementsValidator movementsValidator;

    public MovementsController(MovementService movementService, MovementsValidator movementsValidator) {
        this.movementService = movementService;
        this.movementsValidator = movementsValidator;
    }

    @GetMapping("/movements")
    public Flux<MovementDto> getAllMovments() {
        return movementService.getAllMovements();

    }

    @GetMapping("/movements/{id}")
    public Mono<MovementDto> getMovementById(@PathVariable int id) {
        return movementService.getMovementById(id).
                switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto no enconrado "
                        + id + "no encontrado"
                )));
    }

    @GetMapping("/movements/client/{clientId}")
    public Flux<MovementDto> getMovementsByClientId(@PathVariable int clientId) {
        return movementService.getMovementByClientIdn(clientId);
    }


    @PostMapping("/movements")
    public Mono<MovementDto> postMovement(@RequestBody MovementDto movementDto) {
        return movementsValidator.validator(movementDto).
                flatMap(movementService::postMovement)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    @PutMapping("/movements/{id}")
    public Mono<MovementDto> updataMovementById(@PathVariable int id, @RequestBody MovementDto movementDto) {
        return movementsValidator.validator(movementDto).
                flatMap(updateMovementDto -> movementService.updateMovementById(id, movementDto)).
                switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Objeto no encontrado")));

    }


    @DeleteMapping("/movements/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovementById(@PathVariable int id) {
        return movementService.getMovementById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto no encontrado")
                ))
                .flatMap(m -> movementService.deleteMovementById(id));
    }

}
