package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Client;
import com.pruebaTecnica.Arquitectura.mapper.ClientMapper;
import com.pruebaTecnica.Arquitectura.mapper.MovementMapper;
import com.pruebaTecnica.Arquitectura.repository.ClientRepository;
import com.pruebaTecnica.Arquitectura.repository.MovementRepository;
import com.pruebaTecnica.Arquitectura.service.ClientService;
import com.pruebaTecnica.Arquitectura.service.MovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository _clientRepository;
    private final MovementService _movemenService;


    public ClientServiceImpl(ClientRepository _clientRepository, MovementService movementService) {
        this._clientRepository = _clientRepository;
        this._movemenService = movementService;
    }

    @Override
    public Flux<ClientDto> getAllClients() {
        return _clientRepository.findAll()
                .log()
                .map(ClientMapper::convertToDto);

    }

    @Override
    public Mono<ClientDto> getClientById(int id) {
        return _clientRepository.findById(id).
                log().
                map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<ClientDto> postClient(ClientDto clientDto) {
        Client newClient = ClientMapper.convertToEntity(clientDto);
        return _clientRepository.save(newClient)
                .log()
                .map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<ClientDto> updateClientById(int id, ClientDto clientDto) {
        return _clientRepository.findById(id)
                .flatMap(newClient -> {
                    newClient.setName(clientDto.getName());
                    newClient.setGender(clientDto.getGender());
                    newClient.setIdentification(clientDto.getIdentification());
                    newClient.setAddress(clientDto.getAddress());
                    newClient.setPhoneNumber(clientDto.getPhoneNumber());
                    newClient.setState(clientDto.isState());
                    return _clientRepository.save(newClient);
                })
                .log()
                .map(ClientMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteClientById(int id) {

        return _clientRepository.deleteById(id).log();
    }

    @Override
    public Flux<MovementDto> getMovementsByClient(String identification) {
        return _clientRepository.findByIdentification(identification)
                .flatMapMany(
                        client -> {
                            return _movemenService.getMovementByClientIdn(client.getId());
                        }
                );

    }
}
