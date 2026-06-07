package com.pruebaTecnica.Arquitectura.repository;

import com.pruebaTecnica.Arquitectura.entity.persistence.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountRepository extends ReactiveCrudRepository<Account,Integer> {
}
