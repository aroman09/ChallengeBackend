package com.pichincha.app.person.client.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.pichincha.app.person.client.model.entity.Client;
import com.pichincha.app.person.client.model.ClientDTO;
import com.pichincha.app.person.client.model.entity.Person;

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
    ClientDTO toDTO(Client client, Person person);

    @Mappings({
            @Mapping(source = "clientDTO.clientId", target = "clientId"),
            @Mapping(source = "clientDTO.password", target = "password"),
            @Mapping(source = "clientDTO.state", target = "state"),
            @Mapping(source = "personId", target = "personId")
    })
    Client toEntity(ClientDTO clientDTO,Long personId);

    @Mappings({
            @Mapping(source = "clientDTO.name", target = "name"),
            @Mapping(source = "clientDTO.gender", target = "gender"),
            @Mapping(source = "clientDTO.age", target = "age"),
            @Mapping(source = "clientDTO.identification", target = "identification"),
            @Mapping(source = "clientDTO.direction", target = "direction"),
            @Mapping(source = "clientDTO.telephone", target = "telephone")
    })
    Person toEntityPerson(ClientDTO clientDTO);
    @Mapping(target = "clientId", ignore = true)
    void updateClientFromDTO(ClientDTO clientDTO, @MappingTarget Client existingClient);
    @Mapping(target = "personId", ignore = true)
    void updatePersonFromDTO(ClientDTO clientDTO, @MappingTarget Person existingPerson);

}
