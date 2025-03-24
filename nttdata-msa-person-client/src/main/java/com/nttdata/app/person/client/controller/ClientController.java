package com.nttdata.app.person.client.controller;

import com.nttdata.app.person.client.model.ClientDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.nttdata.app.person.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@Slf4j
@RequestMapping("/app/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    @GetMapping
    public ResponseEntity<Flux<ClientDto>> getAllClients() {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,method);
        Flux<ClientDto>response =clientService.getAllClients();
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/identification/{identification}")
    public Mono<ResponseEntity<ClientDto>> getClientByIdentification(@PathVariable("identification") String identification) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"identification client "+identification,method);
        return clientService.getClientByIdentification(identification)
                .doOnNext(c -> log.info(RESPONSE, c.getName(), method))
                .map(createdClient -> ResponseEntity.status(HttpStatus.OK).body(createdClient));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientDto>> getClientById(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id client "+id,method);
        return clientService.getClientById(id)
                .doOnNext(c -> log.info(RESPONSE, c.getName(), method))
                .map(createdClient -> ResponseEntity.status(HttpStatus.OK).body(createdClient));
    }

    @PostMapping
    public Mono<ResponseEntity<ClientDto>>  createClient(@Valid @RequestBody ClientDto cliente) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"Body client "+cliente,method);
        return clientService.createClient(cliente)
                .doOnNext(c -> log.info(RESPONSE, c.getClientId(), method))
                .map(createdClient -> ResponseEntity.status(HttpStatus.CREATED).body(createdClient));
    }

    @PutMapping
    public Mono<ResponseEntity<ClientDto>>  updateClient(@Valid @RequestBody ClientDto cliente) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"client "+cliente,method);
        return clientService.updateClient(cliente)
                .doOnNext(c -> log.info(RESPONSE, c.getClientId(), method))
                .map(createdClient -> ResponseEntity.status(HttpStatus.CREATED).body(createdClient));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable("id") Long id) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"id client "+id,method);
        return clientService.deleteClient(id)
                .then(Mono.fromSupplier(() -> {
                    log.info(RESPONSE, "Delete client OK", method);
                    return ResponseEntity.noContent().build();
                }));
    }
}
