package com.pichincha.app.person.client.service.impl;

import lombok.RequiredArgsConstructor;
import com.pichincha.app.person.client.exception.ExceptionResponse;
import com.pichincha.app.person.client.model.entity.Client;
import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.mapper.ClientMapper;
import com.pichincha.app.person.client.model.entity.Person;
import com.pichincha.app.person.client.repository.ClientRepository;
import com.pichincha.app.person.client.service.ClientService;
import com.pichincha.app.person.client.service.PersonService;
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
    public Flux<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .flatMap(client ->
                        personService.getPersonById(client.getPersonId())
                                .map(person -> clientMapper.toDTO(client, person))
                );
    }

    @Override
    public Mono<ClientDTO> getClientByIdentification(String identification) {
        return personService.getPersonByIdentification(identification)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"El cliente no existe!!")))
                .flatMap(person ->
                        clientRepository.findById(person.getPersonId())
                                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"No existen datos del cliente!!")))
                        .map(client -> clientMapper.toDTO(client,person))
        );
    }

    @Override
    public Mono<ClientDTO> getClientById(Long id) {
        return clientRepository.findById(id)
                    .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"No existe el cliente!!")))
                    .flatMap(client ->
                       personService.getPersonById(client.getPersonId())
                               .map(person -> clientMapper.toDTO(client, person))
                       );
    }

    @Override
    public Mono<ClientDTO> createClient(ClientDTO clientDTO) {
        return personService.createPerson(clientDTO)
                .cast(Person.class)
                .flatMap(savePerson -> {
                    Client client = clientMapper.toEntity(clientDTO,savePerson.getPersonId());
                   return clientRepository.save(client)
                           .map(saveClient-> clientMapper.toDTO(client,savePerson));
                });
    }

    @Override
    public Mono<ClientDTO> updateClient(ClientDTO cliente) {

        return personService.updatePerson(cliente)
                .flatMap(personExist -> findClientByPersonId(personExist.getPersonId())
                        .flatMap(clientExist -> {
                            clientMapper.updateClientFromDTO(cliente,clientExist);
                            System.out.println(clientExist);
                            return clientRepository.save(clientExist)
                                    .map(saveClient-> clientMapper.toDTO(saveClient,personExist));
                        }));
    }

    @Override
    public Mono<Void> deleteClient(Long id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"El cliente no existe!!")))
                .flatMap(client -> personService.deletePerson(client.getPersonId()));
    }

    @Override
    public Mono<Void> deleteClientByIdentification(String identification) {
        return personService.deletePersonByIdentification(identification);
    }

    private Mono<Client> findClientByPersonId(Long personId){
        return clientRepository.findByPersonId(personId)
                .doOnNext(client -> System.out.println("Cliente encontrado: " + client))
                .switchIfEmpty(Mono.error(new ExceptionResponse(HttpStatus.NOT_FOUND,"El cliente no existe!!")));
    }
}
