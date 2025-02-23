package com.nttdata.app.account.transaction.service.impl;

import com.nttdata.app.account.transaction.exception.ExceptionResponse;
import com.nttdata.app.account.transaction.mapper.TransactionMapper;
import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.entity.Transaction;
import com.nttdata.app.account.transaction.service.AccountService;
import com.nttdata.app.account.transaction.service.TransactionService;
import com.nttdata.app.account.transaction.utils.Message;
import com.nttdata.app.account.transaction.utils.TransactionType;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;

    @Override
    public Flux<TransactionClientResponse> getAllTransaction() {
        return transactionRepository.findAll()
                .flatMap(transaction -> accountService.getAccountByNumber(transaction.getAccount())
                                .map(accountDto -> transactionMapper.toDTOResponse(transaction, accountDto))
                        );
    }

    @Override
    public Flux<TransactionClientResponse> getAllTransactionByClient(Long idClient) {
        return accountService.getAccountByClient(idClient)
                .flatMap(accountDto ->
                            transactionRepository.findAllByAccountOrderByTransactionIdDesc(accountDto.getNumber())
                            .flatMap(transaction ->
                                    getTransactionById(transaction.getTransactionId()))
                );
    }

    @Override
    public Mono<TransactionClientResponse> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND, Message.ERROR_NOT_FOUND_TRANSACTION)))
                .flatMap(transaction -> accountService.getAccountByNumber(transaction.getAccount())
                    .map(accountDto -> transactionMapper.toDTOResponse(transaction, accountDto))
        );
    }

    @Override
    public Mono<TransactionClientResponse> createTransaction(TransactionDto transactionDTO) {
        Transaction transaction = transactionMapper.toEntity(transactionDTO);
        return accountService.getAccountByNumber(transaction.getAccount())
                .flatMap(account ->
                            transactionRepository.findAllByAccountOrderByTransactionIdDesc(transactionDTO.getAccount())
                                    .collectList()
                                    .flatMap(transactions -> {
                                        if (transactions.isEmpty()) transactionDTO.setBalance(account.getInitialBalance());
                                        else transactionDTO.setBalance(transactions.get(0).getBalance());
                                        retrieveMount(transactionDTO);
                                        System.out.println(transactionDTO);
                                        return transactionRepository.save(transactionMapper.toEntity(transactionDTO));
                                    })
                                    .map(savedTransaction -> transactionMapper.toDTOResponse(savedTransaction,account))
                        );
    }

    @Override
    public Mono<Void> deleteTransaction(Long id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_TRANSACTION)))
                .flatMap(transaction -> transactionRepository.deleteById(id));
    }

    private void retrieveMount(TransactionDto transactionDTO){
        Double balance =0.0;
        if (TransactionType.RETIRO.equals(transactionDTO.getType()))
        {
            balance = transactionDTO.getBalance() - Math.abs(transactionDTO.getMount());
            if ( Math.abs(transactionDTO.getMount()) > transactionDTO.getBalance())
                Mono.error(new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE, "No existe saldo suficiente"));
        }
        else
            balance = transactionDTO.getBalance()+ Math.abs(transactionDTO.getMount());
        transactionDTO.setBalance(balance);
    }


}
