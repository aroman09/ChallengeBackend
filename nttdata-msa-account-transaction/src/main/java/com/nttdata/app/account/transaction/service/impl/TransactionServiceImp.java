package com.nttdata.app.account.transaction.service.impl;

import com.nttdata.app.account.transaction.exception.ExceptionResponse;
import com.nttdata.app.account.transaction.mapper.TransactionMapper;
import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.service.AccountService;
import com.nttdata.app.account.transaction.service.TransactionService;
import com.nttdata.app.account.transaction.utils.Message;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImp.class);

    @Override
    public Flux<TransactionClientResponse> getAllTransaction() {
        return transactionRepository.findAll()
                .flatMap(transaction -> accountService.getAccountById(transaction.getAccount())
                                .map(accountDto -> transactionMapper.toDTOResponse(transaction, accountDto, accountDto.getClient()))
                        );
    }

    @Override
    public Flux<TransactionClientResponse> getAllTransactionByDates(LocalDateTime startDate, LocalDateTime endDate, Long idClient) {
        log.info("Fechas para generar reporte: "+startDate+" - "+endDate+" del cliente "+idClient);
        return accountService.getAccountByClient(idClient)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT_CLIENT)))
                .flatMap(account -> transactionRepository.findTransactionsByDateAndAccountNumber(startDate,endDate,account.getIdAccount())
                        .map(transaction -> transactionMapper.toDTOResponse(transaction, account, account.getClient()))
                );
    }

    @Override
    public Flux<TransactionClientResponse> getAllTransactionByClient(Long idClient) {
        return accountService.getAccountByClient(idClient)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_ACCOUNT_CLIENT)))
                .flatMap(accountDto ->
                            transactionRepository.findByAccountIdOrderByDateDesc(accountDto.getIdAccount())
                            .map(transaction -> transactionMapper.toDTOResponse(transaction, accountDto, accountDto.getClient()))
                );
    }

    @Override
    public Mono<TransactionClientResponse> getTransactionById(Long number) {
        return transactionRepository.findById(number)
                .switchIfEmpty( Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND, Message.ERROR_NOT_FOUND_TRANSACTION)))
                .flatMap(transaction -> accountService.getAccountById(transaction.getAccount())
                    .map(accountDto -> transactionMapper.toDTOResponse(transaction, accountDto, accountDto.getClient()))
        );
    }

    @Override
    public Mono<TransactionClientResponse> createTransaction(TransactionDto transactionDTO) {
        return accountService.getAccountByNumber(transactionDTO.getAccount())
                .flatMap(account ->
                            transactionRepository.findByAccountIdOrderByDateDesc(account.getIdAccount())
                                    .collectList()
                                    .flatMap(transactions -> {
                                        if (transactions.isEmpty()) transactionDTO.setInitialBalance(account.getInitialBalance());
                                        else transactionDTO.setInitialBalance(transactions.get(0).getBalance());
                                        retrieveMount(transactionDTO);
                                        System.out.println(transactionDTO);
                                        return transactionRepository.save(transactionMapper.toEntity(transactionDTO,account.getIdAccount()));
                                    })
                                    .map(savedTransaction -> transactionMapper.toDTOResponse(savedTransaction,account, account.getClient()))
                        );
    }

    @Override
    public Mono<Void> deleteTransaction(Long id) {
        return transactionRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_TRANSACTION)))
                .flatMap(transaction -> transactionRepository.deleteById(id));
    }

    private void retrieveMount(TransactionDto transactionDTO){
        Double balance;
        if (transactionDTO.getMount()<0)
        {
            if (Math.abs(transactionDTO.getMount()) >  transactionDTO.getInitialBalance() )
                throw new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE, "No existe saldo suficiente");
            balance = transactionDTO.getInitialBalance() - Math.abs(transactionDTO.getMount());
        }
        else
            balance = transactionDTO.getInitialBalance()+ Math.abs(transactionDTO.getMount());
        transactionDTO.setBalance(balance);
    }


}
