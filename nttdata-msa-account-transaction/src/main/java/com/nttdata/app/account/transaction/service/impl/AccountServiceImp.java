package com.nttdata.app.account.transaction.service.impl;

import com.nttdata.app.account.transaction.exception.ExceptionResponse;
import com.nttdata.app.account.transaction.mapper.AccountMapper;
import com.nttdata.app.account.transaction.model.Client;
import com.nttdata.app.account.transaction.service.ClientService;
import com.nttdata.app.account.transaction.utils.Message;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.entity.Account;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.repository.AccountRepository;
import com.nttdata.app.account.transaction.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientService clientService;


    @Override
    public Flux<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .flatMap(account -> getClient(account.getClientId())
                                .map(client -> objectDto(account,client))
                );
    }

    private Mono<Client> getClient(Long idClient){
        return clientService.getClientByAccount(idClient);
    }

    private AccountDto objectDto(Account account, Client client){
        AccountDto accountDto = accountMapper.toDto(account);
        accountDto.setClient(client);
        return accountDto;
    }

    @Override
    public Mono<AccountDto> getAccountByNumber(String accountNumber) {
        return getAccountExist(accountNumber)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client ->
                             objectDto(account,client)
                        ));
    }

    @Override
    public Mono<AccountDto> getAccountById(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client ->
                                objectDto(account,client)
                        ));
    }

    @Override
    public Flux<AccountDto> getAccountByClient(Long id) {
        return accountRepository.findAllByClientId(id)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client -> objectDto(account,client)));
    }

    @Override
    public Mono<AccountDto> getAccountClient(String accountNumber) {
        return getAccountExist(accountNumber)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client -> objectDto(account,client)));
    }

    @Override
    public Mono<AccountDto> createAccount(AccountDto accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        return getClient(account.getClientId())
                .onErrorResume(WebClientResponseException.class, ex -> Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado")))
                .flatMap( client -> getAccountExist(account.getNumber())
                        .flatMap(existingAccount -> Mono.error(new ExceptionResponse(HttpStatus.CONFLICT, Message.ERROR_EXIST_ACCOUNT)))
                        .switchIfEmpty(accountRepository.save(account))
                        .cast(Account.class)
                        .map(savedAccount -> objectDto(savedAccount,client))
                );
    }

    @Override
    public Mono<AccountDto> updateAccount(AccountDto accountDTO) {
        return getAccountExist(accountDTO.getNumber())
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(existingAccount ->{
                    accountMapper.updateAccountFromDTO(accountDTO,existingAccount);
                    return accountRepository.save(existingAccount)
                            .map(savedAccount -> accountMapper.toDto(savedAccount));
                });
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(existingAccount -> accountRepository.deleteById(existingAccount.getAccountId()));
    }

    @Override
    public Mono<Void> deleteAccountNumber(String accountNumber) {
        return getAccountExist(accountNumber)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(existingAccount -> accountRepository.delete(existingAccount));
    }

    private Mono<Account> getAccountExist(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }
}
