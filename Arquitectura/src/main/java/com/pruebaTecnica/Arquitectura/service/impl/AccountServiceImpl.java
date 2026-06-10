package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.dto.AccountDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Account;
import com.pruebaTecnica.Arquitectura.mapper.AccountMapper;
import com.pruebaTecnica.Arquitectura.repository.AccountRepository;
import com.pruebaTecnica.Arquitectura.service.AccountService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository _accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this._accountRepository = accountRepository;

    }

    @Override
    public Flux<AccountDto> getAllAccounts() {
        return _accountRepository.findAll().
                log().
                map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> getAccounttById(int id) {
        return _accountRepository.findById(id).
                log().
                map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> postAccount(AccountDto accountDto) {
        Account account = AccountMapper.convertToEntity(accountDto);
        return _accountRepository.save(account)
                .log()
                .map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<AccountDto> updateAccountById(int id, AccountDto accountDto) {
        return _accountRepository.findById(id)
                .flatMap(newAccount ->{
                    newAccount.setNumberAccount(accountDto.getNumberAccount());
                    newAccount.setType(accountDto.getType());
                    newAccount.setInitialBalance(accountDto.getInitialBalance());
                    newAccount.setState(accountDto.isState());
                    newAccount.setClientId(accountDto.getClientId());
                    return _accountRepository.save(newAccount);
                }).log()
                .map(AccountMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteAccountById(int id) {

        return _accountRepository.deleteById(id).log();
    }
}
