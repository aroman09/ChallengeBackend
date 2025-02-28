package com.nttdata.app.person.client.controller;

import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        ResponseEntity<Flux<ClientDto>> responseEntity = clientController.getAllClients();

        StepVerifier.create(responseEntity.getBody())
                .expectNext(clientDto)
                .verifyComplete();
        verify(clientService).getAllClients();
    }

    @Test
    void getClientByIdentification() {
        when(clientService.getClientByIdentification("1234567890")).thenReturn(Mono.just(clientDto));
        StepVerifier.create(clientController.getClientByIdentification("1234567890"))
                .assertNext(response -> {
                    assertEquals(200, response.getStatusCodeValue());
                    assertEquals(clientDto, response.getBody());
                })
                .verifyComplete();

        verify(clientService).getClientByIdentification("1234567890");
    }

    @Test
    void createClient() {
        when(clientService.createClient(any())).thenReturn(Mono.just(clientDto));

        StepVerifier.create(clientController.createClient(clientDto))
                .assertNext(response -> {
                    assertEquals(201, response.getStatusCodeValue());
                    assertEquals(clientDto, response.getBody());
                })
                .verifyComplete();

        verify(clientService).createClient(any());
    }

    @Test
    void deleteClient() {
        when(clientService.deleteClient(clientDto.getClientId())).thenReturn(Mono.empty());
        StepVerifier.create(clientController.deleteClient(clientDto.getClientId()))
                .assertNext(response -> assertEquals(204, response.getStatusCodeValue()))
                .verifyComplete();
        verify(clientService).deleteClient(any());
    }
}