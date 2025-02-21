package com.nttdata.app.person.client.service;

import com.nttdata.app.person.client.model.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientDto> getAllClients();

    Mono<ClientDto> getClientByIdentification(String identification);
    Mono<ClientDto> getClientById(Long id);

    Mono<ClientDto> createClient(ClientDto cliente) ;

    Mono<ClientDto> updateClient(ClientDto cliente) ;
    Mono<Void> deleteClient(Long id);
    Mono<Void> deleteClientByIdentification(String identification);
}
