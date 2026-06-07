package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.dto.AccountDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Account;
import com.pruebaTecnica.Arquitectura.repository.AccountRepository;
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

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    List<Account> accountDtoListProof = List.of(
            new Account(1, "478758", "Ahorro", 2000.00, true, 1),
            new Account(3, "225487", "Credito", 1000.00, true, 2)
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAccounts() {
        when(accountRepository.findAll()).thenReturn(Flux.fromIterable(accountDtoListProof));
        StepVerifier.create(accountService.getAllAccounts())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getAccounttById() {
        Account account = accountDtoListProof.get(0);
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getNumberAccount(),
                account.getType(),
                account.getInitialBalance(),
                account.isState(),
                account.getClientId()
        );
        int id = account.getId();
        when(accountRepository.findById(id))
                .thenReturn(Mono.just(account));
        StepVerifier.create(accountService.getAccounttById(id))
                .expectNext(accountDto)
                .verifyComplete();
    }

    @Test
    void postClient() {
        AccountDto inputAccountDto = new AccountDto(0, "164879", "Ahorro", 12.00, true, 1);

        Account savedAccountInDb = new Account();
        savedAccountInDb.setId(4);
        savedAccountInDb.setNumberAccount("164879");
        savedAccountInDb.setType("Ahorro");
        savedAccountInDb.setInitialBalance(12.00);
        savedAccountInDb.setState(true);
        savedAccountInDb.setClientId(1);
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(savedAccountInDb));

        AccountDto expectedAccountDto = new AccountDto(4, "164879", "Ahorro", 12.0, true, 1);
        StepVerifier.create(accountService.postAccount(inputAccountDto))
                .expectNext(expectedAccountDto)
                .verifyComplete();

    }

    @Test
    void putClient() {
        AccountDto updateAccountDto =  new AccountDto();
        updateAccountDto.setId(1);
        updateAccountDto.setNumberAccount("225487");
        updateAccountDto.setType("Corriente");
        updateAccountDto.setInitialBalance(1000.00);
        updateAccountDto.setState(true);
        updateAccountDto.setClientId(1);

        Account updateAccount = new Account();
        updateAccount.setId(updateAccountDto.getId());
        updateAccount.setNumberAccount(updateAccountDto.getNumberAccount());
        updateAccount.setType(updateAccountDto.getType());
        updateAccount.setInitialBalance(updateAccountDto.getInitialBalance());
        updateAccount.setClientId(updateAccountDto.getClientId());

        int id = updateAccount.getId();
        when(accountRepository.findById(id)).thenReturn(Mono.just(updateAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(Mono.just(updateAccount));
        StepVerifier.create(accountService.updateAccountById(id, updateAccountDto))
                .expectNext(updateAccountDto)
                .verifyComplete();

    }

    @Test
    void deleteAccount(){
        Account erasesdAccount = new Account();
        erasesdAccount.setId(4);
        erasesdAccount.setNumberAccount("164879");
        erasesdAccount.setType("Ahorro");
        erasesdAccount.setInitialBalance(12.00);
        erasesdAccount.setState(true);
        erasesdAccount.setClientId(1);
        int id = erasesdAccount.getId();
        when(accountRepository.findById(id)).thenReturn(Mono.just(erasesdAccount));

        when(accountRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(accountService.deleteAccountById(id))
                .verifyComplete();

    }
}