package com.nttdata.app.account.transaction.service.impl;

import com.nttdata.app.account.transaction.config.WebClientConfig;
import com.nttdata.app.account.transaction.model.Client;
import com.nttdata.app.account.transaction.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final WebClientConfig webClientConfig;


    @Override
    public Mono<Client> getClientByAccount(Long idClient) {
        return webClientConfig.webClient().get()
                .uri("/{id}",idClient)
                .retrieve()
                .bodyToMono(Client.class);
    }
}
