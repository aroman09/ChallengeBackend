package com.pichincha.app.person.client.service;

import com.pichincha.app.person.client.model.ClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    Flux<ClientDTO> getAllClients();

    Mono<ClientDTO> getClientByIdentification(String identification);
    Mono<ClientDTO> getClientById(Long id);

    Mono<ClientDTO> createClient(ClientDTO cliente) ;

    Mono<ClientDTO> updateClient(ClientDTO cliente) ;
    Mono<Void> deleteClient(Long id);
    Mono<Void> deleteClientByIdentification(String identification);
}
