package com.nttdata.app.account.transaction.controller;


import com.nttdata.app.account.transaction.model.entity.Account;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AccountDto> getAccountByNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDto> createAccount(@RequestBody AccountDto account) {
        Mono<AccountDto> accountDto = accountService.createAccount(account);
        return accountService.createAccount(account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return accountService.deleteAccount(id);
    }

    @DeleteMapping("/number/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccount(@PathVariable("accountNumber") String accountNumber) {
        return accountService.deleteAccountNumber(accountNumber);
    }
}
