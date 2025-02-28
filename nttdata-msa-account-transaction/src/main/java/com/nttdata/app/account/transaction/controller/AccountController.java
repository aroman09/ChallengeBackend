package com.nttdata.app.account.transaction.controller;

import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.service.AccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/app/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private static final String BASE_URL = "/app/accounts";

    @GetMapping
    public ResponseEntity<Flux<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/number/{accountNumber}")
    public Mono<ResponseEntity<AccountDto>> getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByNumber(accountNumber).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<AccountDto>> getAccountById(@PathVariable("id") Long id) {
        return accountService.getAccountById(id).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<AccountDto>>  createAccount(@RequestBody AccountDto account) {
        return accountService.createAccount(account)
                .map(createdAccount -> ResponseEntity
                        .created(URI.create(BASE_URL + "/" + createdAccount.getIdAccount()))
                        .body(createdAccount));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Long id) {
        return accountService.deleteAccount(id).thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/number/{accountNumber}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable("accountNumber") String accountNumber) {
        return accountService.deleteAccountNumber(accountNumber).thenReturn(ResponseEntity.noContent().build());
    }
}
