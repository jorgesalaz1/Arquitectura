package com.pruebaTecnica.Arquitectura.repository;

import com.pruebaTecnica.Arquitectura.entity.persistence.Movement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MovementRepository extends ReactiveCrudRepository<Movement, Integer> {
    Mono<Movement> findTopByAccountIdOrderByDateDesc(int accountId);
    Mono<Movement> findTopByAccountIdAndIdLessThanOrderByIdDesc(Integer accountId, Integer id);
}
