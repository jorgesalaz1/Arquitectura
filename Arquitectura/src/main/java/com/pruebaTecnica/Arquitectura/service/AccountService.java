package com.pruebatecnica.arquitectura.service;

import com.pruebatecnica.arquitectura.dto.AccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountDto> getAllAccounts();
    Mono<AccountDto> getAccounttById(int id);
    Mono<AccountDto> postAccount(AccountDto accountDto);
    Mono<AccountDto> updateAccountById(int id, AccountDto accountDto);
    Mono<Void> deleteAccountById(int id);



}
