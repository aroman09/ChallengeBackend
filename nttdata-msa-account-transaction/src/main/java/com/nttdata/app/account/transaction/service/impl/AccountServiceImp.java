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
                                .map(client -> accountMapper.toDtoClient(account,client)));
    }

    public Mono<Client> getClient(Long idClient){
        return clientService.getClientByAccount(idClient);
    }

    @Override
    public Mono<AccountDto> getAccountByNumber(String accountNumber) {
        return getAccountExist(accountNumber)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client ->
                             accountMapper.toDtoClient(account,client)
                        ));
    }

    @Override
    public Flux<AccountDto> getAccountByClient(Long id) {
        return accountRepository.findAllByClientId(id)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client -> accountMapper.toDtoClient(account,client)));
    }

    @Override
    public Mono<AccountDto> getAccountClient(String accountNumber) {
        return getAccountExist(accountNumber)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT)))
                .flatMap(account -> getClient(account.getClientId())
                        .map(client -> {
                            return accountMapper.toDtoClient(account,client);
                        }));
    }

    @Override
    public Mono<AccountDto> createAccount(AccountDto accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        return getAccountExist(account.getNumber())
                .flatMap(
                        existingAccount -> Mono.error(new ExceptionResponse(HttpStatus.CONFLICT, Message.ERROR_EXIST_ACCOUNT))
                )
                .cast(AccountDto.class)
                .switchIfEmpty(
                        accountRepository.save(account)
                                .map(accountMapper::toDto)
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
