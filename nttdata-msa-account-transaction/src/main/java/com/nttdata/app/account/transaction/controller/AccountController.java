package com.nttdata.app.account.transaction.controller;


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
    public Mono<AccountDto> getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDto> createAccount(@RequestBody AccountDto account) {
        System.out.println("Received account: " + account);
        return accountService.createAccount(account).cast(AccountDto.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return accountService.deleteAccount(id);
    }

    @DeleteMapping("/number/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable String accountNumber) {
        return accountService.deleteAccountNumber(accountNumber);
    }
}
