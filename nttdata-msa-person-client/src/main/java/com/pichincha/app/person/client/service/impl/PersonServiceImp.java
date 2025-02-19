package com.pichincha.app.person.client.service.impl;

import lombok.RequiredArgsConstructor;
import com.pichincha.app.person.client.exception.ExceptionResponse;
import com.pichincha.app.person.client.mapper.ClientMapper;
import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.model.entity.Person;
import com.pichincha.app.person.client.repository.PersonRepository;
import com.pichincha.app.person.client.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {

    private final PersonRepository personRepository;
    private final ClientMapper clientMapper;


    @Override
    public Flux<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> getPersonByIdentification(String identification) {
        return personRepository.findByIdentification(identification);
    }

    @Override
    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Mono<Object> createPerson(ClientDTO clientDTO) {
        Person person = clientMapper.toEntityPerson(clientDTO);
        return getPersonByIdentification(clientDTO.getIdentification())
                .flatMap(existingPerson -> Mono.error(new ExceptionResponse(HttpStatus.CONFLICT,
                        String.format("La persona con la identificación %s ya existe!!", existingPerson.getIdentification())
                )))
                .switchIfEmpty(personRepository.save(person));
    }

    @Override
    public Mono<Person> updatePerson(ClientDTO clientDTO) {
        return getPersonByIdentification(clientDTO.getIdentification())
                .flatMap(existingPerson -> {
                    clientMapper.updatePersonFromDTO(clientDTO,existingPerson);
                    System.out.println(existingPerson);
                    return personRepository.save(existingPerson);
                })
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,
                        String.format("La persona con la identificación %s no existe!!", clientDTO.getIdentification()))));
    }

    @Override
    public Mono<Void> deletePerson(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"El cliente no existe!!")))
                .flatMap(person -> personRepository.deleteById(person.getPersonId()));
    }

    @Override
    public Mono<Void> deletePersonByIdentification(String identification) {
        return personRepository.findByIdentification(identification)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"El cliente no existe!!")))
                .flatMap(person -> personRepository.delete(person));
    }
}
