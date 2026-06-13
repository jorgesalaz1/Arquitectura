package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.service.ClientMovementService;
import com.pruebaTecnica.Arquitectura.service.ClientService;
import com.pruebaTecnica.Arquitectura.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientMovementServiceImpl implements ClientMovementService {
    private WebClient _webClient;
    private MovementService _movementService;
    private ClientService _clientService;

    public ClientMovementServiceImpl(WebClient _webClient, MovementService movementService, ClientService clientService) {
        this._webClient = _webClient;
        this._movementService = movementService;
        this._clientService = clientService;
    }


    @Override
    public Flux<MovementDto> getClientByIdentification(String identification) {
        return _clientService.getClientByIdentificationReal(identification)
                .flatMapMany(clientDto -> {
                    return _webClient.get()
                            .uri("movements/client/{clientUd}", clientDto.getId())
                            .retrieve()
                            .bodyToFlux(MovementDto.class)
                            .onErrorResume(
                                    WebClientResponseException.NotFound.class,
                                    throwable -> Flux.error(new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,"El cleinte no registra movimientos"
                                    ))
                            );
                });
    }
}
