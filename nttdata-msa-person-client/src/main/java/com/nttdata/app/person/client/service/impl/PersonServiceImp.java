package com.nttdata.app.person.client.service.impl;

import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.utils.Message;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.person.client.exception.ExceptionResponse;
import com.nttdata.app.person.client.mapper.ClientMapper;
import com.nttdata.app.person.client.model.entity.Person;
import com.nttdata.app.person.client.repository.PersonRepository;
import com.nttdata.app.person.client.service.PersonService;
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
    public Mono<Person> getPersonByIdentification(String identification) {
        return personRepository.findByIdentification(identification);
    }

    @Override
    public Mono<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Mono<Object> createPerson(ClientDto clientDTO) {
        Person person = clientMapper.toEntityPerson(clientDTO);
        return getPersonByIdentification(clientDTO.getIdentification())
                .flatMap(existingPerson -> Mono.error(new ExceptionResponse(HttpStatus.CONFLICT,
                        String.format(Message.ERROR_EXIST_PERSON, existingPerson.getIdentification())
                )))
                .switchIfEmpty(personRepository.save(person));
    }

    @Override
    public Mono<Person> updatePerson(ClientDto clientDTO) {
        return getPersonByIdentification(clientDTO.getIdentification())
                .flatMap(existingPerson -> {
                    clientMapper.updatePersonFromDTO(clientDTO,existingPerson);
                    return personRepository.save(existingPerson);
                })
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,
                        String.format(Message.ERROR_NOT_FOUND_PERSON, clientDTO.getIdentification()))));
    }

    @Override
    public Mono<Void> deletePerson(Long id) {
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_CLIENT)))
                .flatMap(person -> personRepository.deleteById(person.getPersonId()));
    }

    @Override
    public Mono<Void> deletePersonByIdentification(String identification) {
        return personRepository.findByIdentification(identification)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND, Message.ERROR_NOT_FOUND_CLIENT)))
                .flatMap(person -> personRepository.delete(person));
    }
}
