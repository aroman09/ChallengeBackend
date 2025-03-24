package com.nttdata.app.account.transaction.controller;


import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
@Slf4j
@RestController
@RequestMapping("/app/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    @GetMapping
    public ResponseEntity<Flux<TransactionClientResponse>> getAllTransaction() {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,method);
        Flux<TransactionClientResponse> response = transactionService.getAllTransaction();
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{idClient}")
    public ResponseEntity<Flux<TransactionClientResponse>> getAllTransactionByClient(@PathVariable("idClient") Long idClient)  {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id client "+idClient ,method);
        Flux<TransactionClientResponse> response = transactionService.getAllTransactionByClient(idClient);
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{account}")
    public ResponseEntity<Flux<TransactionClientResponse>> getAllTransactionByAccount(@PathVariable("account") String account)  {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"account "+account,method);
        Flux<TransactionClientResponse> response = transactionService.getAllTransactionByAccount(account);
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionClientResponse>> getTransactionById(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id Transaction",method);
        return transactionService.getTransactionById(id)
                .doOnNext(c -> log.info(RESPONSE, c.getIdTransaction(), method))
                .map(created -> ResponseEntity.status(HttpStatus.OK).body(created));
    }

    @PostMapping
    public Mono<ResponseEntity<TransactionClientResponse>> createTransaction(@RequestBody TransactionDto transactionDTO) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"transaction "+transactionDTO,method);
        return transactionService.createTransaction(transactionDTO)
                .doOnNext(c -> log.info(RESPONSE, c.getIdTransaction(), method))
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransaction(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id" +id,method);
        return transactionService.deleteTransaction(id)
                .then(Mono.fromSupplier(() -> {
                    log.info(RESPONSE, "Delete transaction ok", method);
                    return ResponseEntity.noContent().build();
                }));
    }
}
