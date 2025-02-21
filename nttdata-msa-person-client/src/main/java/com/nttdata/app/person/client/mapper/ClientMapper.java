package com.nttdata.app.person.client.mapper;

import com.nttdata.app.person.client.model.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.nttdata.app.person.client.model.entity.Client;
import com.nttdata.app.person.client.model.entity.Person;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mappings({
            @Mapping(source = "client.clientId", target = "clientId"),
            @Mapping(source = "client.password", target = "password"),
            @Mapping(source = "client.state", target = "state"),
            @Mapping(source = "person.name", target = "name"),
            @Mapping(source = "person.gender", target = "gender"),
            @Mapping(source = "person.age", target = "age"),
            @Mapping(source = "person.identification", target = "identification"),
            @Mapping(source = "person.direction", target = "direction"),
            @Mapping(source = "person.telephone", target = "telephone")
    })
    ClientDto toDTO(Client client, Person person);

    @Mappings({
            @Mapping(source = "clientDto.clientId", target = "clientId"),
            @Mapping(source = "clientDto.password", target = "password"),
            @Mapping(source = "clientDto.state", target = "state"),
            @Mapping(source = "personId", target = "personId")
    })
    Client toEntity(ClientDto clientDto, Long personId);

    @Mappings({
            @Mapping(source = "clientDto.name", target = "name"),
            @Mapping(source = "clientDto.gender", target = "gender"),
            @Mapping(source = "clientDto.age", target = "age"),
            @Mapping(source = "clientDto.identification", target = "identification"),
            @Mapping(source = "clientDto.direction", target = "direction"),
            @Mapping(source = "clientDto.telephone", target = "telephone")
    })
    Person toEntityPerson(ClientDto clientDto);
    @Mapping(target = "clientId", ignore = true)
    void updateClientFromDTO(ClientDto clientDto, @MappingTarget Client existingClient);
    @Mapping(target = "personId", ignore = true)
    void updatePersonFromDTO(ClientDto clientDto, @MappingTarget Person existingPerson);

}
