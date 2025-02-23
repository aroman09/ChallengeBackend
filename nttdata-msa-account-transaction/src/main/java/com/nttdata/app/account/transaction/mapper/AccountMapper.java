package com.nttdata.app.account.transaction.mapper;

import com.nttdata.app.account.transaction.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.entity.Account;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountMapper {

   @Mappings({
            @Mapping(source = "accountDto.idAccount", target = "accountId"),
            @Mapping(source = "accountDto.number", target = "number"),
            @Mapping(source = "accountDto.type", target = "type"),
            @Mapping(source = "accountDto.initialBalance", target = "initialBalance"),
            @Mapping(source = "accountDto.status", target = "status"),
            @Mapping(source = "accountDto.client.id", target = "clientId"),
    })
    Account toEntity(AccountDto accountDto);

    @Mappings({
            @Mapping(source = "account.accountId", target = "idAccount"),
            @Mapping(source = "account.number", target = "number"),
            @Mapping(source = "account.type", target = "type"),
            @Mapping(source = "account.initialBalance", target = "initialBalance"),
            @Mapping(source = "account.status", target = "status"),
            @Mapping(source = "account.clientId", target = "client.id"),
    })
    AccountDto toDto(Account account);

    @Mappings({
            @Mapping(source = "account.accountId", target = "idAccount"),
            @Mapping(source = "account.number", target = "number"),
            @Mapping(source = "account.type", target = "type"),
            @Mapping(source = "account.initialBalance", target = "initialBalance"),
            @Mapping(source = "account.status", target = "status"),
            @Mapping(source = "client.id", target = "client.id"),
            @Mapping(source = "client.identification", target = "client.identification"),
            @Mapping(source = "client.name", target = "client.name"),
    })
    AccountDto toDtoClient(Account account, Client client);

    @Mapping(target = "accountId", ignore = true)
    void updateAccountFromDTO(AccountDto accountDTO, @MappingTarget Account account);


}
