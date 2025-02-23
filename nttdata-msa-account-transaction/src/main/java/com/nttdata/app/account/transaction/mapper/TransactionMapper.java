package com.nttdata.app.account.transaction.mapper;

import com.nttdata.app.account.transaction.model.AccountDto;
import com.nttdata.app.account.transaction.model.Client;
import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.nttdata.app.account.transaction.model.TransactionDto;
import com.nttdata.app.account.transaction.model.entity.Transaction;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mappings({
            @Mapping(source = "transaction.dateTransaction", target = "dateTransaction"),
            @Mapping(source = "transaction.type", target = "typeTransaction"),
            @Mapping(source = "transaction.mount", target = "mount"),
            @Mapping(source = "transaction.balance", target = "balance"),
            @Mapping(source = "accountDto.client.name", target = "name"),
            @Mapping(source = "accountDto.number", target = "number"),
            @Mapping(source = "accountDto.type", target = "typeAccount"),
            @Mapping(source = "accountDto.initialBalance", target = "initialBalance"),
            @Mapping(source = "accountDto.status", target = "status"),
    })
    TransactionClientResponse toDTOResponse(Transaction transaction, AccountDto accountDto);

    @Mappings({
            @Mapping(source = "transactionDto.dateTransaction", target = "dateTransaction"),
            @Mapping(source = "transactionDto.type", target = "type"),
            @Mapping(source = "transactionDto.mount", target = "mount"),
            @Mapping(source = "transactionDto.balance", target = "balance"),
            @Mapping(source = "transactionDto.account", target = "account"),
    })
    Transaction toEntity(TransactionDto transactionDto);

}
