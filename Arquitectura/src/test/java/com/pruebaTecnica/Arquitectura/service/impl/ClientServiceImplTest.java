package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.dto.AccountDto;
import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Account;
import com.pruebaTecnica.Arquitectura.entity.persistence.Client;
import com.pruebaTecnica.Arquitectura.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    List<Client> clientList = List.of(
            new Client(5, "Juan Gomez", "Masculino", "095286423589","Onceava y Sedalana","268596","12345", true),
            new Client(6, "Solanda Gpyes", "Femenino", "0986325896","Ceibos Norte","2892652","12345", true)
    );


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(Flux.fromIterable(clientList));
        StepVerifier.create(clientService.getAllClients())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getClientById() {
        Client client = clientList.get(0);
        ClientDto clientDto = new ClientDto(
                client.getId(),
                client.getName(),
                client.getGender(),
                client.getIdentification(),
                client.getAddress(),
                client.getPhoneNumber(),
                client.getPassword(),
                client.isState()
        );
        int id = client.getId();
        when(clientRepository.findById(id))
                .thenReturn(Mono.just(client));
        StepVerifier.create(clientService.getClientById(id))
                .expectNext(clientDto)
                .verifyComplete();

    }

    @Test
    void postClient() {
        ClientDto inputClientDto = new ClientDto(0, "Pedro Suarez", "Masculino", "0932857493","Boyaca y Padre Solano","2968593","17896" ,true);

        Client saveClientInDb = new Client();
        saveClientInDb.setId(5);
        saveClientInDb.setName("Pedro Suarez");
        saveClientInDb.setGender("Masculino");
        saveClientInDb.setIdentification("0932857493");
        saveClientInDb.setAddress("Boyaca y Padre Solano");
        saveClientInDb.setPhoneNumber("2968593");
        saveClientInDb.setPassword("17896");
        saveClientInDb.setState(true);
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(saveClientInDb));
        ClientDto expectedClientDto = new ClientDto(5,"Pedro Suarez", "Masculino", "0932857493", "Boyaca y Padre Solano","2968593","17896",true);
        StepVerifier.create(clientService.postClient(inputClientDto))
                .expectNext(expectedClientDto)
                .verifyComplete();

    }

 @Test
    void updateClientById() {
        ClientDto updateClientDto = new ClientDto();
        updateClientDto.setId(5);
        updateClientDto.setName("Karla Haro");
        updateClientDto.setGender("Femenino");
        updateClientDto.setIdentification("0932857493");
        updateClientDto.setAddress("Boyaca y Padre Solano");
        updateClientDto.setPhoneNumber("6001245");
        updateClientDto.setPassword("Nathalia");
        updateClientDto.setState(true);

        Client updateClient = new Client();
        updateClient.setId(updateClientDto.getId());
        updateClient.setName(updateClientDto.getName());
        updateClient.setGender(updateClientDto.getGender());
        updateClient.setIdentification(updateClientDto.getIdentification());
        updateClient.setAddress(updateClientDto.getAddress());
        updateClient.setPhoneNumber(updateClientDto.getPhoneNumber());
        updateClient.setPassword(updateClientDto.getPassword());
        updateClient.setState(updateClientDto.isState());


        int id = updateClient.getId();
        when(clientRepository.findById(id)).thenReturn(Mono.just(updateClient));
        when(clientRepository.save(any(Client.class))).thenReturn(Mono.just(updateClient));
        StepVerifier.create(clientService.updateClientById(id, updateClientDto))
                .expectNext(updateClientDto)
                .verifyComplete();

    }

    @Test
    void deleteClientById() {
        Client erasedClient = new Client();
        erasedClient.setId(5);
        erasedClient.setName("Karla Haro");
        erasedClient.setGender("Femenino");
        erasedClient.setIdentification("0932857493");
        erasedClient.setAddress("Boyaca y Padre Solano");
        erasedClient.setPhoneNumber("6001245");
        erasedClient.setPassword("Nathalia");
        erasedClient.setState(true);
        int id = erasedClient.getId();
        when(clientRepository.findById(id)).thenReturn(Mono.just(erasedClient));

        when(clientRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(clientService.deleteClientById(id))
                .verifyComplete();

    }
}