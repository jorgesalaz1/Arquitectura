package com.pruebatecnica.arquitectura.service;

import com.pruebatecnica.arquitectura.dto.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientDto> getAllClients();
    Mono<ClientDto> getClientById(int id);
    Mono<ClientDto> postClient(ClientDto clientDto);
    Mono<ClientDto> updateClientById(int id, ClientDto clientDto);
    Mono<Void> deleteClientById(int id);
    Mono<ClientDto> getClientByIdentificationReal(String identification);

}
