package com.nttdata.app.person.client.controller;

import com.nttdata.app.person.client.model.ClientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.person.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/app/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;
    private static final String BASE_URL = "/app/clients";

    @GetMapping
    public ResponseEntity<Flux<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/identification/{identification}")
    public Mono<ResponseEntity<ClientDto>> getClientByIdentification(@PathVariable("identification") String identification) {
        return clientService.getClientByIdentification(identification).map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientDto>> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id).map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<ClientDto>>  createClient(@Valid @RequestBody ClientDto cliente) {
        return clientService.createClient(cliente).map(createdClient -> ResponseEntity
                .created(URI.create(BASE_URL + "/" + createdClient.getClientId()))
                .body(createdClient));
    }

    @PutMapping
    public Mono<ResponseEntity<ClientDto>>  updateClient(@Valid @RequestBody ClientDto cliente) {
        return clientService.updateClient(cliente).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id).thenReturn(ResponseEntity.noContent().build());
    }
}
