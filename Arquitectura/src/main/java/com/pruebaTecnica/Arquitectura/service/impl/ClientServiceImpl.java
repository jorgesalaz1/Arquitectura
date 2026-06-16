package com.pruebatecnica.arquitectura.service.impl;

import com.pruebatecnica.arquitectura.dto.ClientDto;
import com.pruebatecnica.arquitectura.entity.persistence.Client;
import com.pruebatecnica.arquitectura.mapper.ClientMapper;
import com.pruebatecnica.arquitectura.repository.ClientRepository;
import com.pruebatecnica.arquitectura.service.ClientService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;



    public ClientServiceImpl(ClientRepository clientRepository/* MovementService movementService*/) {
        this.clientRepository = clientRepository;

    }

    @Override
    public Flux<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .log()
                .map(ClientMapper::convertToDto);

    }

    @Override
    public Mono<ClientDto> getClientById(int id) {
        return clientRepository.findById(id).
                log().
                map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<ClientDto> postClient(ClientDto clientDto) {
        Client newClient = ClientMapper.convertToEntity(clientDto);
        return clientRepository.save(newClient)
                .log()
                .map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<ClientDto> updateClientById(int id, ClientDto clientDto) {
        return clientRepository.findById(id)
                .flatMap(newClient -> {
                    newClient.setName(clientDto.getName());
                    newClient.setGender(clientDto.getGender());
                    newClient.setIdentification(clientDto.getIdentification());
                    newClient.setAddress(clientDto.getAddress());
                    newClient.setPhoneNumber(clientDto.getPhoneNumber());
                    newClient.setState(clientDto.isState());
                    return clientRepository.save(newClient);
                })
                .log()
                .map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteClientById(int id) {

        return clientRepository.deleteById(id).log();
    }

    @Override
    public Mono<ClientDto> getClientByIdentificationReal(String identification) {
        return clientRepository.getClientByIdentification(identification)
                .log().map(ClientMapper::convertToDto);
    }
}
