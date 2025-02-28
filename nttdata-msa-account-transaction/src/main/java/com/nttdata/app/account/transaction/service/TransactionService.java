package com.nttdata.app.account.transaction.service;


import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.TransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

public interface TransactionService {
    Flux<TransactionClientResponse> getAllTransaction();
    Flux<TransactionClientResponse> getAllTransactionByDates(LocalDateTime startDate, LocalDateTime endDate, Long idClient);
    Flux<TransactionClientResponse> getAllTransactionByClient(Long idClient);

    Mono<TransactionClientResponse> getTransactionById(Long id) ;

    Mono<TransactionClientResponse> createTransaction(TransactionDto transactionDTO) ;

    Mono<Void> deleteTransaction(Long id);
}
