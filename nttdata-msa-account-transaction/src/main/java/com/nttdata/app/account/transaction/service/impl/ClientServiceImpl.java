package com.nttdata.app.account.transaction.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.app.account.transaction.model.Client;
import com.nttdata.app.account.transaction.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final WebClient webClient;

    @Override
    public Mono<Client> getClientByAccount(Long idClient) {
        Mono<Client> client = webClient.get()
                .uri("/{id}",idClient)
                .retrieve()
                .bodyToMono(Client.class)
                .doOnNext(clientnew -> log.info("Cliente recibido: {}", clientnew))
                .doOnError(err -> log.error("Error al obtener cliente", err));
        return client;
    }
}
