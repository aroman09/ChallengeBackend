package com.nttdata.app.person.client.controller;

import com.nttdata.app.person.client.model.ClientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.person.client.service.ClientService;
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
    public Flux<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/identification/{identification}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDto> getClientByIdentification(@PathVariable("identification") String identification) {
        return clientService.getClientByIdentification(identification);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDto> getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientDto>  createClient(@Valid @RequestBody ClientDto cliente) {
        return clientService.createClient(cliente);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDto>  updateClient(@Valid @RequestBody ClientDto cliente) {
        return clientService.updateClient(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }
}
