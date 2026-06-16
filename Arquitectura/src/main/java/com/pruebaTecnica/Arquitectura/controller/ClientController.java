package com.pruebatecnica.arquitectura.controller;


import com.pruebatecnica.arquitectura.dto.ClientDto;
import com.pruebatecnica.arquitectura.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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

    @GetMapping("/clients/cedula/{identification}")
    public Mono<ClientDto> getClientByIdentification(@PathVariable String identification) {
        return clientService.getClientByIdentificationReal(identification)
                .switchIfEmpty(Mono.error(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Cliente no encontrado con esa cédula"
                )));
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
