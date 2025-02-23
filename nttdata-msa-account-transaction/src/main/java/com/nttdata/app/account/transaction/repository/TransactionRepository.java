package com.nttdata.app.account.transaction.repository;

import com.nttdata.app.account.transaction.model.entity.Transaction;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import java.time.LocalDate;

@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction,Long> {

    @Query("SELECT m FROM movimientos m WHERE m.dateTransaction BETWEEN :fechaInicio AND :fechaFin AND m.accountNumber = :cuenta")
    Flux<Transaction> findTransactionsByDateAndAccountNumber(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin,
            @Param("cuenta") String cuenta
    );

    Flux<Transaction> findAllByAccountOrderByTransactionIdDesc(String account);
}
