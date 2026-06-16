package com.pruebatecnica.arquitectura.service;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import reactor.core.publisher.Flux;

public interface ClientMovementService {
     Flux<MovementDto> getClientByIdentification (String identification);
}
