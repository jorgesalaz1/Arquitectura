package com.pruebatecnica.arquitectura.service.impl;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.service.ClientMovementService;
import com.pruebatecnica.arquitectura.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@Service
public class ClientMovementServiceImpl implements ClientMovementService {
    private final WebClient webClient;
    private final ClientService clientService;

    public ClientMovementServiceImpl(WebClient webClientConstructor, ClientService clientServiceConstructor) {
        this.webClient = webClientConstructor;
        this.clientService = clientServiceConstructor;
    }


    @Override
    public Flux<MovementDto> getClientByIdentification(String identification) {
        return clientService.getClientByIdentificationReal(identification)
                .flatMapMany(clientDto ->
                        webClient.get()
                                .uri("movements/client/{clientUd}", clientDto.getId())
                                .retrieve()
                                .bodyToFlux(MovementDto.class)
                                .onErrorResume(
                                        WebClientResponseException.NotFound.class,
                                        throwable -> Flux.error(new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "El cliente no registra movimientos"
                                        ))
                                )
                );
    }
}
