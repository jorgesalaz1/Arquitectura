package com.pruebaTecnica.Arquitectura.repository;

import com.pruebaTecnica.Arquitectura.entity.persistence.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientRepository extends ReactiveCrudRepository<Client,Integer> {
  Mono<Client> getClientByIdentification(String identification);
}
