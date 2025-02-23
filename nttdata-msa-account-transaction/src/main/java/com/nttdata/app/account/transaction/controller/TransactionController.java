package com.nttdata.app.account.transaction.controller;

import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionClientResponse> getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/client/{idClient}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionClientResponse> getAllTransactionByClient(@PathVariable Long idClient) {
        return transactionService.getAllTransactionByClient(idClient);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<TransactionClientResponse> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionClientResponse> createTransaction(@RequestBody TransactionDto transactionDTO) {
        System.out.println("Received client: " + transactionDTO);
        return transactionService.createTransaction(transactionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTransaction(@PathVariable Long id) {
        return transactionService.deleteTransaction(id);
    }
}
