package com.nttdata.app.account.transaction.repository;

import com.nttdata.app.account.transaction.model.entity.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction,Long> {

    @Query("SELECT * FROM transactions m WHERE m.date_transaction BETWEEN :startDate AND :endDate AND m.id_account = :accountId")
    Flux<Transaction> findTransactionsByDateAndAccountNumber(
            @Param("startDate") LocalDateTime fechaInicio,
            @Param("endDate") LocalDateTime fechaFin,
            @Param("accountId") Long cuenta
    );

    @Query("SELECT * FROM transactions m WHERE m.id_account = :accountId ORDER BY m.date_transaction DESC")
    Flux<Transaction> findByAccountIdOrderByDateDesc(@Param("accountId") Long accountId);

}
