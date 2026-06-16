package com.pruebatecnica.arquitectura.repository;

import com.pruebatecnica.arquitectura.entity.persistence.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountRepository extends ReactiveCrudRepository<Account,Integer> {
}
