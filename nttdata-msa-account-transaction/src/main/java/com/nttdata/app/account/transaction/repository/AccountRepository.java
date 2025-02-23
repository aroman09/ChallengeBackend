package com.nttdata.app.account.transaction.repository;

import com.nttdata.app.account.transaction.model.entity.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account,Long> {

    Flux<Account> findAllByClientId(Long clientId);
    Mono<Account> findByNumber(String accountNumber);
}
