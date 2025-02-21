package com.nttdata.app.person.client.repository;

import com.nttdata.app.person.client.model.entity.Client;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client,Long> {
    Mono<Client> findByPersonId(Long personId);
}
