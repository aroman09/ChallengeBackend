package com.nttdata.app.person.client.service.impl;

import com.nttdata.app.person.client.mapper.ClientMapper;
import com.nttdata.app.person.client.model.ClientDto;
import com.nttdata.app.person.client.model.entity.Client;
import com.nttdata.app.person.client.model.entity.Person;
import com.nttdata.app.person.client.repository.ClientRepository;
import com.nttdata.app.person.client.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class ClientServiceImpTest {
    @InjectMocks
    ClientServiceImp clientServiceImp;
    @Mock
    ClientRepository clientRepository;
    @Mock
    PersonService personService;
    @Mock
    ClientMapper clientMapper;

    Flux<Client> clientsFlux;


    @Test
    void getAllClients() {
        Person person = new Person(1L,"Pedro","M",30,"1234567890","Cuenca","0987654321");
        Client client = new Client(1L,1L,"pass12345",true);
        ClientDto clientDto = new ClientDto().builder()
                .clientId(1L)
                .name("Pedro")
                .gender("M")
                .direction("Cuenca")
                .identification("1234567890")
                .telephone("0987654321")
                .password("pass12345")
                .age(30)
                .build();
        clientsFlux= Flux.just(client);

        when(clientRepository.findAll()).thenReturn(clientsFlux);
        when(personService.getPersonById(1L)).thenReturn(Mono.just(person));
        when(clientMapper.toDTO(any(Client.class),any(Person.class))).thenReturn(clientDto);

        Flux<ClientDto> clients = clientServiceImp.getAllClients();

        StepVerifier.create(clients)
                .expectNextMatches(clientPerson ->
                    clientDto.getClientId() == clientPerson.getClientId() &&
                    clientDto.getPassword().equals(clientPerson.getPassword()) &&
                    clientDto.getName().equals(clientPerson.getName()) &&
                    clientDto.getGender().equals(clientPerson.getGender()) &&
                    clientDto.getAge() == clientPerson.getAge()&&
                    clientDto.getIdentification().equals(clientPerson.getIdentification()) &&
                    clientDto.getDirection().equals(clientPerson.getDirection()) &&
                    clientDto.getTelephone().equals(clientPerson.getTelephone()))
                .verifyComplete();

        verify(clientRepository).findAll();
        verify(personService).getPersonById(1L);
    }

    @Test
    void deleteClientByIdentification() {
        when(personService.deletePersonByIdentification(any())).thenReturn(Mono.empty());

        StepVerifier.FirstStep<Void> deleteClient = StepVerifier.create(clientServiceImp.deleteClientByIdentification("1234567890"));
        deleteClient.expectComplete().verify();

        verify(personService).deletePersonByIdentification("1234567890");

    }
}