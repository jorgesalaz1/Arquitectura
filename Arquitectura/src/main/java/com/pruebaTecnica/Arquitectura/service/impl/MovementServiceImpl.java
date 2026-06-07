package com.pruebaTecnica.Arquitectura.service.impl;

import com.pruebaTecnica.Arquitectura.service.MovementService;
import com.pruebaTecnica.Arquitectura.validator.MovementsValidator;
import com.pruebaTecnica.Arquitectura.dto.MovementDto;
import com.pruebaTecnica.Arquitectura.entity.persistence.Movement;
import com.pruebaTecnica.Arquitectura.mapper.MovementMapper;
import com.pruebaTecnica.Arquitectura.repository.AccountRepository;
import com.pruebaTecnica.Arquitectura.repository.MovementRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository _movementRepository;
    private final AccountRepository _accountRespository;
    private final MovementsValidator _movementValidator;

    public MovementServiceImpl(MovementRepository movementRepository, MovementsValidator movementsValidator,
                               AccountRepository accountRepository) {
        this._movementRepository = movementRepository;
        this._movementValidator = movementsValidator;
        this._accountRespository = accountRepository;
    }

    @Override
    public Flux<MovementDto> getAllMovements() {
        return _movementRepository.findAll().
                log().
                map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> getMovementById(int id) {
        return _movementRepository.findById(id).
                log().
                map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> postMovement(MovementDto movementDto) {
        Movement movement = MovementMapper.convertToEntity(movementDto);

        return _accountRespository.findById(movement.getAccountId()).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no enocntrada"))).
                flatMap(account ->
                {
                   return _movementRepository.findTopByAccountIdOrderByDateDesc(movement.getAccountId()).
                            map(lastMovement -> lastMovement.getBalance())
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
                                                return _movementRepository.save(movement);
                                            }
                                    );
                }).log()
                .map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<MovementDto> updateMovementById(int id, MovementDto movementDto) {
        Movement updateMovement = MovementMapper.convertToEntity(movementDto);
        updateMovement.setId(id);
        return _accountRespository.findById(updateMovement.getAccountId()).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Cuenta no enocntrada"))).
                flatMap(account -> {
                        return _movementRepository.findTopByAccountIdAndIdLessThanOrderByIdDesc(updateMovement.getAccountId(),id).
                            map(lastMovement -> lastMovement.getBalance()).
                            switchIfEmpty(Mono.just(account.getInitialBalance())).
                            flatMap(
                                    currentBalance ->{
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

                                        return _movementRepository.save(updateMovement);
                                    }
                            );
                }).log()
                .map(MovementMapper::convertToDto);
    }

    @Override
    public Mono<Void> deleteMovementById(int id) {
        return _movementRepository
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

}
