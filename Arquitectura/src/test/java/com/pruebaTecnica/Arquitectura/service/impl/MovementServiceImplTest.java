package com.pruebatecnica.arquitectura.service.impl;

import com.pruebatecnica.arquitectura.dto.MovementDto;
import com.pruebatecnica.arquitectura.entity.persistence.Account;
import com.pruebatecnica.arquitectura.entity.persistence.Movement;
import com.pruebatecnica.arquitectura.repository.AccountRepository;
import com.pruebatecnica.arquitectura.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class MovementServiceImplTest {

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MovementServiceImpl movementService;

    List<Movement> movementList =  List.of(
            new Movement(5, LocalDate.of(2026, 5, 13),"Debito",100.00,99.00,5,5),
            new Movement(6, LocalDate.of(2026, 5, 13),"Credito",20.00,110.00,6,5)

    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMovements() {
        when(movementRepository.findAll()).thenReturn(Flux.fromIterable(movementList));
        StepVerifier.create(movementService.getAllMovements())
                .expectNextCount(2)
                .verifyComplete();

    }

    @Test
    void getMovementById() {
        Movement movement = movementList.get(1);
         MovementDto movementDto = new MovementDto(
                movement.getId(),
                movement.getDate(),
                movement.getType(),
                movement.getAmount(),
                movement.getBalance(),
                movement.getClientId(),
                movement.getAccountId()
        );
        int id = movement.getId();
        when(movementRepository.findById(id))
                .thenReturn(Mono.just(movement));
        StepVerifier.create(movementService.getMovementById(id))
                .expectNext(movementDto)
                .verifyComplete();
    }

    @Test
    void postMovement() {
        MovementDto inputMovementDto = new MovementDto(0, LocalDate.of(2026, 5, 14),"Credito",20.00,120.00,6,5);

        Movement saveMovementInDb = new Movement();
        saveMovementInDb.setId(7);
        saveMovementInDb.setDate(LocalDate.of(2026,5,14));
        saveMovementInDb.setType("Credito");
        saveMovementInDb.setAmount(20.00);
        saveMovementInDb.setBalance(120.00);
        saveMovementInDb.setClientId(6);
        saveMovementInDb.setAccountId (5);
        when(movementRepository.save(any(Movement.class))).thenReturn(Mono.just(saveMovementInDb));

        Account mockAccount = new Account();
        mockAccount.setId(5);
        mockAccount.setInitialBalance(110.00);

        when(accountRepository.findById(anyInt())).thenReturn(Mono.just(mockAccount));
        when(movementRepository.findTopByAccountIdOrderByDateDesc(anyInt())).thenReturn(Mono.empty());

        MovementDto expectedMovementDto = new MovementDto(7, LocalDate.of(2026, 5, 14),"Credito",20.00,120.00,6,5);
        StepVerifier.create(movementService.postMovement(inputMovementDto))
                .expectNext(expectedMovementDto)
                .verifyComplete();

    }

    @Test
    void updateMovementById() {
        MovementDto updateMovementDto =  new MovementDto();
        updateMovementDto.setId(7);
        updateMovementDto.setDate(LocalDate.of(2026,5,14));
        updateMovementDto.setType("Debito");
        updateMovementDto.setAmount(20.00);
        updateMovementDto.setBalance(0.00);
        updateMovementDto.setClientId(6);
        updateMovementDto.setAccountId (5);

        Movement updateMovement = new Movement();
        updateMovement.setId(updateMovementDto.getId());
        updateMovement.setDate(updateMovementDto.getDate());
        updateMovement.setType(updateMovementDto.getType());
        updateMovement.setAmount(updateMovementDto.getAmount());
        updateMovement.setBalance(80.00);
        updateMovement.setClientId(updateMovementDto.getClientId());
        updateMovement.setAccountId(updateMovementDto.getAccountId());

        int id = updateMovement.getId();
        Account mockAccount = new Account();
        mockAccount.setId(5);
        mockAccount.setInitialBalance(100.00);

        Movement movementPrevious = new Movement();
        movementPrevious.setBalance(100.00);

        when(accountRepository.findById(anyInt())).thenReturn(Mono.just(mockAccount));
        when(movementRepository.findTopByAccountIdAndIdLessThanOrderByIdDesc(5,7)).thenReturn(Mono.just(movementPrevious));
        when(movementRepository.save(any(Movement.class))).thenReturn(Mono.just(updateMovement));

        MovementDto expectedMovementDto = new MovementDto();
        expectedMovementDto.setId(7);
        expectedMovementDto.setDate(updateMovementDto.getDate());
        expectedMovementDto.setType(updateMovementDto.getType());
        expectedMovementDto.setAmount(updateMovementDto.getAmount());
        expectedMovementDto.setBalance(80.00);
        expectedMovementDto.setClientId(updateMovementDto.getClientId());
        expectedMovementDto.setAccountId(updateMovementDto.getAccountId());

        StepVerifier.create(movementService.updateMovementById(id, updateMovementDto))
                .expectNext(expectedMovementDto)
                .verifyComplete();

    }

    @Test
    void deleteMovementById() {
        Movement erasedMovement = new Movement();
        erasedMovement.setId(7);
        erasedMovement.setDate(LocalDate.of(2026,5,14));
        erasedMovement.setType("Credito");
        erasedMovement.setAmount(10.00);
        erasedMovement.setBalance(120.00);
        erasedMovement.setClientId(6);
        erasedMovement.setAccountId(5);
        int id = erasedMovement.getId();
        when(movementRepository.findById(id)).thenReturn(Mono.just(erasedMovement));

        when(movementRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(movementService.deleteMovementById(id))
                .verifyComplete();

    }
}