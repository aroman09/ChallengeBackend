package com.pichincha.app.person.client.service;


import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.model.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Flux<Person> getAllPersons();

    Mono<Person> getPersonByIdentification(String identification) ;
    Mono<Person> getPersonById(Long id) ;

    Mono<Object> createPerson(ClientDTO clientDTO);

    Mono<Person> updatePerson(ClientDTO clientDTO);
    Mono<Void> deletePerson(Long id);
    Mono<Void> deletePersonByIdentification(String identification);

}
