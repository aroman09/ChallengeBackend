package com.nttdata.app.person.client.repository;

import com.nttdata.app.person.client.model.entity.Client;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<Client,Long> {
    Mono<Client> findByPersonId(Long personId);

    @Query("SELECT id_client, id_person, password, state FROM clients")
    Flux<Client> findAllClients();
}
