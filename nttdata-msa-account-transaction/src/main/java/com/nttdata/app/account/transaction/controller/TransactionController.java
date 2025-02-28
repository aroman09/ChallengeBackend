package com.nttdata.app.account.transaction.controller;


import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/app/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private static final String BASE_URL = "/app/transactions";

    @GetMapping
    public ResponseEntity<Flux<TransactionClientResponse>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @GetMapping("/client/{idClient}")
    public Flux<TransactionClientResponse> getAllTransactionByClient(@PathVariable Long idClient) {
        return transactionService.getAllTransactionByClient(idClient);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionClientResponse>> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionClientResponse>> createTransaction(@RequestBody TransactionDto transactionDTO) {
        return transactionService.createTransaction(transactionDTO)
                .map(createdTransaction -> ResponseEntity
                        .created(URI.create(BASE_URL + "/" + createdTransaction.getIdTransaction()))
                        .body(createdTransaction));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable Long id) {
        return transactionService.deleteTransaction(id).thenReturn(ResponseEntity.noContent().build());
    }
}
