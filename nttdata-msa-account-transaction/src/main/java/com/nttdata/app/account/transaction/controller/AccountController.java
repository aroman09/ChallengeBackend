package com.nttdata.app.account.transaction.controller;

import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/app/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    @GetMapping
    public ResponseEntity<Flux<AccountDto>> getAllAccounts() {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,method);
        Flux<AccountDto> response = accountService.getAllAccounts();
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{accountNumber}")
    public Mono<ResponseEntity<AccountDto>> getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"Account "+accountNumber,method);
        return accountService.getAccountByNumber(accountNumber)
                .doOnNext(c -> log.info(RESPONSE, c.getIdAccount(), method))
                .map(created -> ResponseEntity.status(HttpStatus.OK).body(created));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountDto>> getAccountById(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"Id Account "+id,method);
        return accountService.getAccountById(id)
                .doOnNext(c -> log.info(RESPONSE, c.getIdAccount(), method))
                .map(created -> ResponseEntity.status(HttpStatus.OK).body(created));
    }

    @PostMapping
    public Mono<ResponseEntity<AccountDto>>  createAccount(@RequestBody AccountDto account) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"Body account "+account,method);
        return accountService.createAccount(account)
                .doOnNext(c -> log.info(RESPONSE, c.getIdAccount(), method))
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @PutMapping
    public Mono<ResponseEntity<AccountDto>> updatedAccount(@RequestBody AccountDto account) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"Body account "+account,method);
        return accountService.updateAccount(account)
                .doOnNext(c -> log.info(RESPONSE,c.getIdAccount(),method))
                .map(created -> ResponseEntity.status(HttpStatus.CREATED).body(created));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id account "+id,method);
        return accountService.deleteAccount(id)
                .then(Mono.fromSupplier(() -> {
                    log.info(RESPONSE, "Delete account OK", method);
                    return ResponseEntity.noContent().build();
                }));
    }

    @DeleteMapping("/number/{accountNumber}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable("accountNumber") String accountNumber) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"account number "+accountNumber,method);
        return accountService.deleteAccountNumber(accountNumber)
                .then(Mono.fromSupplier(() -> {
                    log.info(RESPONSE, "Delete account number ok", method);
                    return ResponseEntity.noContent().build();
                }));
    }
}
