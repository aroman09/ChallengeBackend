package com.pichincha.app.person.client.controller;

import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Autowired
    ClientController clientController;

    @Mock
    ClientService clientService;

    @Test
    void getAllClients() {
        Flux<ClientDTO> clientDTOFlux = new ArrayList<>();
        when(clientService.getAllClients()).thenReturn(new ArrayList<>());
    }

    @Test
    void getClientByIdentification() {
    }

    @Test
    void getClientById() {
    }

    @Test
    void createClient() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
    }
}