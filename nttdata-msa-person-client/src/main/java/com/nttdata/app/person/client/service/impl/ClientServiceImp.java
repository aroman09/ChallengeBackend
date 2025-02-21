package com.nttdata.app.person.client.service.impl;

import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.utils.Message;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.person.client.exception.ExceptionResponse;
import com.nttdata.app.person.client.model.entity.Client;
import com.nttdata.app.person.client.mapper.ClientMapper;
import com.nttdata.app.person.client.model.entity.Person;
import com.nttdata.app.person.client.repository.ClientRepository;
import com.nttdata.app.person.client.service.ClientService;
import com.nttdata.app.person.client.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    private final PersonService personService;
    private final ClientMapper clientMapper;

    @Override
    public Flux<ClientDto> getAllClients() {
        return clientRepository.findAll()
                .flatMap(client ->
                        personService.getPersonById(client.getPersonId())
                                .map(person -> clientMapper.toDTO(client, person))
                );
    }

    @Override
    public Mono<ClientDto> getClientByIdentification(String identification) {
        return personService.getPersonByIdentification(identification)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND, Message.ERROR_NOT_FOUND_CLIENT)))
                .flatMap(person ->
                        clientRepository.findById(person.getPersonId())
                                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_INFO_CLIENT)))
                        .map(client -> clientMapper.toDTO(client,person))
        );
    }

    @Override
    public Mono<ClientDto> getClientById(Long id) {
        return clientRepository.findById(id)
                    .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_CLIENT)))
                    .flatMap(client ->
                       personService.getPersonById(client.getPersonId())
                               .map(person -> clientMapper.toDTO(client, person))
                       );
    }

    @Override
    public Mono<ClientDto> createClient(ClientDto clientDTO) {
        return personService.createPerson(clientDTO)
                .cast(Person.class)
                .flatMap(savePerson -> {
                    Client client = clientMapper.toEntity(clientDTO,savePerson.getPersonId());
                   return clientRepository.save(client)
                           .map(saveClient-> clientMapper.toDTO(client,savePerson));
                });
    }

    @Override
    public Mono<ClientDto> updateClient(ClientDto cliente) {

        return personService.updatePerson(cliente)
                .flatMap(personExist -> findClientByPersonId(personExist.getPersonId())
                        .flatMap(clientExist -> {
                            clientMapper.updateClientFromDTO(cliente,clientExist);
                            return clientRepository.save(clientExist)
                                    .map(saveClient-> clientMapper.toDTO(saveClient,personExist));
                        }));
    }

    @Override
    public Mono<Void> deleteClient(Long id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_CLIENT)))
                .flatMap(client -> personService.deletePerson(client.getPersonId()));
    }

    @Override
    public Mono<Void> deleteClientByIdentification(String identification) {
        return personService.deletePersonByIdentification(identification);
    }

    private Mono<Client> findClientByPersonId(Long personId){
        return clientRepository.findByPersonId(personId)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,Message.ERROR_NOT_FOUND_CLIENT)));
    }
}
