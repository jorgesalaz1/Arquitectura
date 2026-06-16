package com.pruebatecnica.arquitectura.controller;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.service.ClientMovementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/")
public class ClientMovementController {
    private ClientMovementService clentMovementService;

    public ClientMovementController(ClientMovementService clentMovementService) {
        this.clentMovementService = clentMovementService;
    }

    @GetMapping("movementsIdentif/{identification}")
    public Flux<MovementDto> getByCedula(@PathVariable String identification) {
        return clentMovementService.getClientByIdentification(identification);
    }
}
