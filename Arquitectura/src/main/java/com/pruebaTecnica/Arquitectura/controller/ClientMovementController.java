package com.pruebaTecnica.Arquitectura.controller;

import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.service.ClientMovementService;
import com.pruebaTecnica.Arquitectura.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/")
public class ClientMovementController {
    private ClientMovementService _clentMovementService;

    public ClientMovementController(ClientMovementService _clentMovementService) {
        this._clentMovementService = _clentMovementService;
    }

    @GetMapping("movementsIdentif/{identification}")
    public Flux<MovementDto> getByCedula(@PathVariable String identification) {
        return _clentMovementService.getClientByIdentification(identification);
    }
}
