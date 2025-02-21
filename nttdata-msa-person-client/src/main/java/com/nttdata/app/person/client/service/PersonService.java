package com.nttdata.app.person.client.service;


import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.model.entity.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {

    Mono<Person> getPersonByIdentification(String identification) ;
    Mono<Person> getPersonById(Long id) ;

    Mono<Object> createPerson(ClientDto clientDTO);

    Mono<Person> updatePerson(ClientDto clientDTO);
    Mono<Void> deletePerson(Long id);
    Mono<Void> deletePersonByIdentification(String identification);

}
