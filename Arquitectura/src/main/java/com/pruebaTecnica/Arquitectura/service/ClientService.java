package com.pruebaTecnica.Arquitectura.service;

import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientDto> getAllClients();
    Mono<ClientDto> getClientById(int id);
    Mono<ClientDto> postClient(ClientDto clientDto);
    Mono<ClientDto> updateClientById(int id, ClientDto clientDto);
    Mono<Void> deleteClientById(int id);
    Flux<MovementDto> getMovementsByClient(String identification);

}
