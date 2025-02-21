package com.nttdata.app.person.client.controller;

import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ClientControllerTest {
    @InjectMocks
    ClientController clientController;

    @Mock
    ClientService clientService;

    static ClientDto clientDto;

    @BeforeAll
    static void setUpBeforeAllTests() {
        clientDto = new ClientDto();
        clientDto.setClientId(1L);
        clientDto.setIdentification("1234567890");
        clientDto.setName("Pruebas");
        clientDto.setDirection("Cuenca");
        clientDto.setTelephone("0987654321");
        clientDto.setAge(30);
    }

    @Test
    void getAllClients() {
        when(clientService.getAllClients()).thenReturn(Flux.just(clientDto));

        Flux<ClientDto> responseGet = clientController.getAllClients();

        StepVerifier.create(responseGet)
                .expectNext(clientDto)
                .verifyComplete();
        verify(clientService).getAllClients();
    }

    @Test
    void getClientByIdentification() {
        when(clientService.getClientByIdentification("1234567890")).thenReturn(Mono.just(clientDto));
        StepVerifier.FirstStep<ClientDto> client = StepVerifier.create(clientController.getClientByIdentification("1234567890"));
        client.expectNext(clientDto);
        client.expectComplete().verify();

        verify(clientService).getClientByIdentification("1234567890");
    }

    @Test
    void createClient() {
        when(clientService.createClient(any())).thenReturn(Mono.just(clientDto));

        StepVerifier.create(clientController.createClient(any()))
                .expectNext(clientDto)
                .verifyComplete();

        verify(clientService).createClient(any());
    }

    @Test
    void deleteClient() {
        when(clientService.deleteClient(clientDto.getClientId())).thenReturn(Mono.empty());
        StepVerifier.create(clientService.deleteClient(clientDto.getClientId()))
                .verifyComplete();

        verify(clientService).deleteClient(any());
    }
}