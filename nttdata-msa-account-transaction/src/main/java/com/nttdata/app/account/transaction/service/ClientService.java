package com.nttdata.app.account.transaction.service;

import com.nttdata.app.account.transaction.model.Client;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<Client> getClientByAccount(Long idClient) ;
}
