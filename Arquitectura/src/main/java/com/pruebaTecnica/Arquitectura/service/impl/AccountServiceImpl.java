package com.pruebatecnica.arquitectura.service.impl;

import com.pruebatecnica.arquitectura.dto.AccountDto;
import com.pruebatecnica.arquitectura.entity.persistence.Account;
import com.pruebatecnica.arquitectura.mapper.AccountMapper;
import com.pruebatecnica.arquitectura.repository.AccountRepository;
import com.pruebatecnica.arquitectura.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AccountServiceImpl implements AccountService {
    private final  AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepositoryConstructor) {
        this.accountRepository = accountRepositoryConstructor;

    }

    @Override
    public Flux<AccountDto> getAllAccounts() {
        return accountRepository.findAll().
                log().
                map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> getAccounttById(int id) {
        return accountRepository.findById(id).
                log().
                map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> postAccount(AccountDto accountDto) {
        Account account = AccountMapper.convertToEntity(accountDto);
        return accountRepository.save(account)
                .log()
                .map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> updateAccountById(int id, AccountDto accountDto) {
        return accountRepository.findById(id)
                .flatMap(newAccount ->{
                    newAccount.setNumberAccount(accountDto.getNumberAccount());
                    newAccount.setType(accountDto.getType());
                    newAccount.setInitialBalance(accountDto.getInitialBalance());
                    newAccount.setState(accountDto.isState());
                    newAccount.setClientId(accountDto.getClientId());
                    return accountRepository.save(newAccount);
                }).log()
                .map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteAccountById(int id) {

        return accountRepository.deleteById(id).log();
    }
}
