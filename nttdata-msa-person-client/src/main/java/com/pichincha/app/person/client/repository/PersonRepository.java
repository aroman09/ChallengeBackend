package com.pichincha.app.person.client.repository;

import com.pichincha.app.person.client.model.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person,Long> {
    Mono<Person> findByIdentification(String identification);
}
