package com.pruebatecnica.arquitectura.service.impl;

import com.pruebatecnica.arquitectura.service.MovementService;
import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.entity.persistence.Movement;
import com.pruebatecnica.arquitectura.mapper.MovementMapper;
import com.pruebatecnica.arquitectura.repository.AccountRepository;
import com.pruebatecnica.arquitectura.repository.MovementRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRespository;


    public MovementServiceImpl(MovementRepository movementRepository,
                               AccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRespository = accountRepository;
    }

    @Override
    public Flux<MovementDto> getAllMovements() {
        return movementRepository.findAll().
                log().
                map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> getMovementById(int id) {
        return movementRepository.findById(id).
                log().
                map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> postMovement(MovementDto movementDto) {
        Movement movement = MovementMapper.convertToEntity(movementDto);

        return accountRespository.findById(movement.getAccountId()).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no enocntrada"))).
                flatMap(account ->

                        movementRepository.findTopByAccountIdOrderByDateDesc(movement.getAccountId()).
                                map(Movement::getBalance)
                                .switchIfEmpty(Mono.just(account.getInitialBalance())).
                                flatMap
                                        (
                                                currentBalance ->
                                                {
                                                    double newResultBalance = calculatNewBalance(currentBalance, movementDto);
                                                    if ("DEBITO".equalsIgnoreCase(movementDto.getType()) && newResultBalance < 0) {
                                                        return Mono.error(new IllegalArgumentException("Saldo insuficiente"));
                                                    }
                                                    movement.setBalance(newResultBalance);
                                                    return movementRepository.save(movement);
                                                })
                ).log()
                .map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> updateMovementById(int id, MovementDto movementDto) {
        Movement updateMovement = MovementMapper.convertToEntity(movementDto);
        updateMovement.setId(id);
        return accountRespository.findById(updateMovement.getAccountId()).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no enocntrada"))).
                flatMap(account ->
                        movementRepository.findTopByAccountIdAndIdLessThanOrderByIdDesc(updateMovement.getAccountId(), id).
                                map(Movement::getBalance).
                                switchIfEmpty(Mono.just(account.getInitialBalance())).
                                flatMap(
                                        currentBalance -> {
                                            double newResultBalance = calculatNewBalance(currentBalance, movementDto);
                                            if ("DEBITO".equalsIgnoreCase(movementDto.getType()) && newResultBalance < 0) {
                                                return Mono.error(new IllegalArgumentException("Saldo insuficiente"));
                                            }
                                            updateMovement.setDate(movementDto.getDate());
                                            updateMovement.setType(movementDto.getType());
                                            updateMovement.setAmount(movementDto.getAmount());
                                            updateMovement.setBalance(newResultBalance);
                                            updateMovement.setClientId(movementDto.getClientId());
                                            updateMovement.setAccountId(movementDto.getAccountId());

                                            return movementRepository.save(updateMovement);
                                        })
                ).log()
                .map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteMovementById(int id) {
        return movementRepository
                .deleteById(id).log();
    }

    @Override
    public Double calculatNewBalance(double currentBalance, MovementDto movementDto) {
        if ("Debito".equalsIgnoreCase(movementDto.getType())) {
            return currentBalance - movementDto.getAmount();
        } else {
            return currentBalance + movementDto.getAmount();
        }
    }

    @Override
    public Flux<MovementDto> getMovementByClientIdn(int id) {
        return movementRepository.findByClientId(id)
                .log()
                .map(MovementMapper::convertToDto);
    }

}
