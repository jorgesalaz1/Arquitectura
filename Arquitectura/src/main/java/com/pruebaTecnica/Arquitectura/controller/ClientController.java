package com.pruebaTecnica.Arquitectura.controller;


import com.pruebaTecnica.Arquitectura.dto.AccountDto;
import com.pruebaTecnica.Arquitectura.dto.ClientDto;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Client;
import com.pruebaTecnica.Arquitectura.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService _clientService) {
        this.clientService = _clientService;
    }

    @GetMapping("/clients")
    public Flux<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/clients/{id}")
    public Mono<ClientDto> getClientById(@PathVariable int id) {
        return clientService.getClientById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con el id "
                        + id +  " no encontrado"
                )));
    }

    @GetMapping("/clients/{identification}/movements")
    public Flux<MovementDto> getClientByMovements(@PathVariable  String identification){
        return clientService.getMovementsByClient(identification).
                switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente con el numero de ceedula "
                + identification + " no posee movimieentos en su cuenta")));
    }

    @PostMapping("/clients")
    public Mono<ClientDto> postClient(@RequestBody ClientDto clientDto){
        return clientService.postClient(clientDto);
    }

    @PutMapping("/clients/{id}")
    public Mono<ClientDto> updateClientById(@PathVariable int id , @RequestBody ClientDto clientDto){
        return clientService.updateClientById(id, clientDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con el id "
                        + id +  " no encontrado"
                )));

    }

    @DeleteMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClientById(@PathVariable int id){
        return clientService.getClientById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con id  "
                         + id + " no encontrado "
                        )
                ))
                .flatMap(c -> clientService.deleteClientById(id) );

    }
}
