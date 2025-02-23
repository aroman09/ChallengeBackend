package com.nttdata.app.account.transaction.service;


import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<AccountDto> getAllAccounts();
    Flux<AccountDto> getAccountByClient(Long id);
    Mono<AccountDto> getAccountByNumber(String accountNumber) ;
    Mono<AccountDto> getAccountClient(String accountNumber);

    Mono<AccountDto> createAccount(AccountDto account) ;
    Mono<AccountDto> updateAccount(AccountDto account) ;

    Mono<Void> deleteAccount(Long id);
    Mono<Void> deleteAccountNumber(String accountNumber);
}
