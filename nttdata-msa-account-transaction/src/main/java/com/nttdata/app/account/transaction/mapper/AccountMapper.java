package com.nttdata.app.account.transaction.mapper;

import org.mapstruct.*;
import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

 @Mappings({
            @Mapping(source = "accountDto.idAccount", target = "accountId"),
            @Mapping(source = "accountDto.number", target = "number"),
            @Mapping(source = "accountDto.type", target = "type"),
            @Mapping(source = "accountDto.initialBalance", target = "initialBalance"),
            @Mapping(source = "accountDto.status", target = "status"),
            @Mapping(source = "accountDto.client.clientId", target = "clientId"),
    })
    Account toEntity(AccountDto accountDto);

    @Mappings({
            @Mapping(source = "account.accountId", target = "idAccount"),
            @Mapping(source = "account.number", target = "number"),
            @Mapping(source = "account.type", target = "type"),
            @Mapping(source = "account.initialBalance", target = "initialBalance"),
            @Mapping(source = "account.status", target = "status"),
    })
    AccountDto toDto(Account account);

@Mapping(target = "accountId", ignore = true)
@Mapping(target = "clientId", ignore = true)
@Mapping(target = "number", ignore = true)
@Mapping(target = "initialBalance", ignore = true)
void updateAccountFromDTO(AccountDto accountDTO, @MappingTarget Account account);
}
