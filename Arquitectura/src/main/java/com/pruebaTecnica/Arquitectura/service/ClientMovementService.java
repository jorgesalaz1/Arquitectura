package com.pruebaTecnica.Arquitectura.service;

import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientMovementService {
    public Flux<MovementDto> getClientByIdentification (String identification);
}
