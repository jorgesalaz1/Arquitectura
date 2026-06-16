package com.pruebatecnica.arquitectura.controller;


import com.pruebatecnica.arquitectura.dto.AccountDto;
import com.pruebatecnica.arquitectura.service.AccountService;
import com.pruebatecnica.arquitectura.validator.MovementsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;
    private final MovementsValidator movementsValidator;

    public AccountController(AccountService accountService, MovementsValidator movementsValidator) {
        this.accountService = accountService;
        this.movementsValidator = movementsValidator;
    }

    @GetMapping("/accounts")
    public Flux<AccountDto> getAllAccouts() {
        return accountService.getAllAccounts();

    }

    @GetMapping("/accounts/{id}")
    public Mono<AccountDto> getAccountById(@PathVariable int id) {
        return accountService.getAccounttById(id).
                switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto no enconrado "
                        + id + " no encntrado "
                )));
    }

    @PostMapping("/accounts")
    public Mono<AccountDto> postAccount(@RequestBody AccountDto accountDto) {
        return accountService.postAccount(accountDto);
    }

    @PutMapping("/accounts/{id}")
    public Mono<AccountDto> updataAccountById(@PathVariable int id, @RequestBody AccountDto accountDto) {
        return accountService.updateAccountById(id, accountDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto no encontrado")));
    }


    @DeleteMapping("/accounts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccountById(@PathVariable int id) {
        return accountService.getAccounttById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "objeto no encontrado")
                ))
                .flatMap(a -> accountService.deleteAccountById(id));
    }


}
