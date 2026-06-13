package com.pruebaTecnica.Arquitectura.controller;

import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.service.ClientMovementService;
import com.pruebaTecnica.Arquitectura.service.MovementService;
import com.pruebaTecnica.Arquitectura.service.impl.MovementServiceImpl;
import com.pruebaTecnica.Arquitectura.validator.MovementsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class MovementsController {
    private final MovementService movementService;
    private final MovementsValidator movementsValidator;
    private final ClientMovementService _clientMovmentService;

    public MovementsController(MovementService movementService, MovementsValidator movementsValidator, ClientMovementService clientMovementService) {
        this.movementService = movementService;
        this.movementsValidator = movementsValidator;
        this._clientMovmentService = clientMovementService;
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
