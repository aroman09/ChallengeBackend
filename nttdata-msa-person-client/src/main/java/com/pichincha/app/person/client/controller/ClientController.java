package com.pichincha.app.person.client.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app/person/client")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/identification/{identification}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> getClientByIdentification(@PathVariable String identification) {
        return clientService.getClientByIdentification(identification);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientDTO>  createClient(@Valid @RequestBody ClientDTO cliente) {
        return clientService.createClient(cliente);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO>  updateClient(@Valid @RequestBody ClientDTO cliente) {
        return clientService.updateClient(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
